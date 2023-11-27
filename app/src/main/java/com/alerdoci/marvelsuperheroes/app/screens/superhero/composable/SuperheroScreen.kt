package com.alerdoci.marvelsuperheroes.app.screens.superhero.composable

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.common.states.ResourceState
import com.alerdoci.marvelsuperheroes.app.common.states.error.ErrorScreen
import com.alerdoci.marvelsuperheroes.app.common.states.loading.LoadingScreen
import com.alerdoci.marvelsuperheroes.app.components.DiagonalDivider
import com.alerdoci.marvelsuperheroes.app.screens.superhero.viewmodel.SuperHeroViewModel
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.orange_A200
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.red_800
import com.alerdoci.marvelsuperheroes.app.theme.spacing
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.mock.marvelSuperHeroComicMock1
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import me.saket.telephoto.zoomable.rememberZoomableState
import me.saket.telephoto.zoomable.zoomable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SuperheroScreen(
    viewModel: SuperHeroViewModel = hiltViewModel(),
    superheroId: Int
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val superHeroState by viewModel.currentSuperHero.collectAsState()
    val superHeroComicState by viewModel.currentSuperHeroComic.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadSuperHero(superheroId)
    }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) {
        val state = superHeroState
        val comic = superHeroComicState
        when (state) {
            is ResourceState.Loading -> Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LoadingScreen()
            }

            is ResourceState.Error -> Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                ErrorScreen()
            }

            is ResourceState.Success ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val superHeroes = state.data as List<ModelResult>
                    val superHero = superHeroes[0]

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            HeaderSection(
                                title = superHero.name!!,
                                image = superHero.image!!
                            )
                        }
                        item {
                            CharacterInfoSection(
                                description = superHero.description!!
                            )
                        }
                    }
                }

            ResourceState.Idle -> {
                ErrorScreen()
            }
        }

        when (comic) {
            is ResourceState.Loading -> Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LoadingScreen()
            }

            is ResourceState.Error -> Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                ErrorScreen()
            }

            is ResourceState.Success ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val superHeroComic = comic.data as List<ModelComicsResult>
                    ComicsSection(superHeroComic)
                }

            ResourceState.Idle -> {
                ErrorScreen()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            Arrangement.Bottom,
        ) {
            MarvelAttribution()
        }
    }
}

@Composable
fun HeaderSection(
    title: String,
    image: String
) {
    Box(
        modifier = Modifier
            .height(400.dp)
            .fillMaxWidth()
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (diagonalDivider, boxBackgroundImage, characterImage, characterName) = createRefs()

            // Box
            Box(
                modifier = Modifier
                    .constrainAs(boxBackgroundImage) {
                        bottom.linkTo(characterImage.bottom)
                        start to parent.start
                        end to parent.end
                    }
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(red_800)
            )
            // Triangle
            DiagonalDivider(
                modifier = Modifier
                    .constrainAs(diagonalDivider) {
                        bottom.linkTo(boxBackgroundImage.top)
                        start to parent.start
                        end to parent.end
                    }
                    .height(60.dp)
                    .zIndex(1f)
                    .rotate(180f)

            )

            SubcomposeAsyncImage(
                model = image,
                contentDescription =
                stringResource(
                    id = R.string.photo_content_description,
                    title
                ),
                loading = {
                    Box(modifier = Modifier.padding(MaterialTheme.spacing.extraHuge)) {
                        CircularProgressIndicator(
                            color = red_800
                        )
                    }
                },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(characterImage) {
                        top to parent.top
                        start to parent.start
                        end to parent.end
                    }
                    .zIndex(1f)
                    .padding(12.dp)
                    .clip(
                        CutCornerShape(
                            topStart = MaterialTheme.spacing.extraMedium,
                            topEnd = MaterialTheme.spacing.extraMedium
                        )
                    )
                    .fillMaxSize()
                    .zoomable(
                        rememberZoomableState()
                    )
                    .drawWithCache {
                        onDrawWithContent {
                            drawContent()
                            drawRect(
                                Brush.verticalGradient(
                                    0.5f to MarvelColors.black.copy(alpha = 0F),
                                    1F to MarvelColors.black
                                )
                            )
                        }
                    },
            )
            // Character Name
            Text(
                text = title,
                style = MaterialTheme.typography.displayMedium,
                color = Color.White,
                modifier = Modifier
                    .zIndex(1f)
                    .constrainAs(characterName) {
                        bottom.linkTo(characterImage.bottom)
                        start.linkTo(characterImage.start)
                        end.linkTo(characterImage.end)
                    }
                    .paddingFromBaseline(bottom = 40.dp)
            )
        }
    }
}

@Composable
fun CharacterInfoSection(
    description: String
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        val (descriptionTitle, descriptionText, wikiButton) = createRefs()

        Text(
            text = stringResource(id = R.string.description),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(descriptionTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .drawBehind {
                    val verticalOffset = size.height
                    drawLine(
                        color = orange_A200,
                        strokeWidth = 1.dp.toPx(),
                        start = Offset(0f, verticalOffset),
                        end = Offset(size.width, verticalOffset)
                    )
                }
        )

        Text(
            text = description,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(descriptionText) {
                    top.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
                .paddingFromBaseline(top = 20.dp)
        )

        Button(
            onClick = { },
            modifier = Modifier
                .constrainAs(wikiButton) {
                    top.linkTo(descriptionText.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "See full image")
        }
    }
}

@Composable
fun ComicsSection(comicListItems: List<ModelComicsResult>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Comics",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.paddingFromBaseline(top = 32.dp)
        )

        ComicsList(comicListItems = comicListItems)
    }
}

@Composable
fun MarvelAttribution() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(Color.Red)
    ) {
        Text(
            text = "Data provided by Marvel. © 2023 MARVEL",
            color = Color.White,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun ComicsList(comicListItems: List<ModelComicsResult>) {
    Column {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraMedium)) {
            items(comicListItems) { item ->
                ComicCard(item = item)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ComicCard(
    item: ModelComicsResult
) {
    Box(
        modifier = Modifier
            .height(220.dp)
            .width(170.dp)
            .padding(end = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(
            model = item.image,
            contentDescription =
            stringResource(
                id = R.string.photo_content_description,
                item.title!!
            ),
            loading = {
                Box(modifier = Modifier.padding(MaterialTheme.spacing.extraHuge)) {
                    CircularProgressIndicator(
                        color = red_800
                    )
                }
            },
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CutCornerShape(topStart = MaterialTheme.spacing.extraMedium))
                .fillMaxSize()
                .drawWithCache {
                    onDrawWithContent {
                        drawContent()
                        drawRect(
                            Brush.verticalGradient(
                                0.5f to MarvelColors.black.copy(alpha = 0F),
                                1F to MarvelColors.black
                            )
                        )
                    }
                },
        )

        Text(
            text = item.title,
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.small,
                    end = MaterialTheme.spacing.small,
                    bottom = MaterialTheme.spacing.extraLarge
                )
                .align(Alignment.BottomCenter)
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    delayMillis = 0,
                    initialDelayMillis = 3000
                ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
        Text(
            text = item.onSaleDate!!,
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.small,
                    end = MaterialTheme.spacing.small,
                    bottom = MaterialTheme.spacing.small
                )
                .align(Alignment.BottomCenter)
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    delayMillis = 0,
                    initialDelayMillis = 3000
                ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}


@Preview("Light Theme", showBackground = true)
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ComicCardPreview() {
    ComicCard(marvelSuperHeroComicMock1)
}


@Preview("Light Theme", showBackground = true)
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SuperheroScreenPreview() {
    SuperheroScreen(
//        viewModel = mockViewModel,
        superheroId = 1011334
    )
}
