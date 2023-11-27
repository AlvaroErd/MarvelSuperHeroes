package com.alerdoci.marvelsuperheroes.app.screens.superhero.composable

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.common.states.ResourceState
import com.alerdoci.marvelsuperheroes.app.common.states.error.ErrorScreen
import com.alerdoci.marvelsuperheroes.app.common.states.loading.LoadingScreen
import com.alerdoci.marvelsuperheroes.app.screens.superhero.viewmodel.SuperHeroViewModel
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.red_800
import com.alerdoci.marvelsuperheroes.app.theme.spacing
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsSuperHeroList
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import me.saket.telephoto.zoomable.rememberZoomableState
import me.saket.telephoto.zoomable.zoomable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        when (val state = superHeroState) {
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
                    modifier = Modifier.fillMaxSize()
                ) {
                    val superHeroes = state.data as? List<ModelResult>
                    if (!superHeroes.isNullOrEmpty()) {
                        val superHero = superHeroes[0]
                        val name = superHero.name
                        val description = superHero.description
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            item {
                                HeaderSection(title = name!!, image = superHero.image!!)
                            }
                            item {
                                CharacterInfoSection(description = description!!)
                            }
                            item {
//                                ComicsSection(currentSuperHeroComic)
                            }
                            item {
                                MarvelAttribution()
                            }
                        }
                    }
                }

            ResourceState.Idle -> {
                ErrorScreen()
            }
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
            .height(250.dp)
            .fillMaxWidth()
    ) {
        // Replace with your actual character image
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            contentDescription = "",
            loading = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        color = red_800
                    )
                }
            },
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .zoomable(
                    rememberZoomableState()
                )
                .clip(CutCornerShape(topStart = MaterialTheme.spacing.extraMedium))
                .aspectRatio(1 / 1f),
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = title, // Replace with character name
            style = MaterialTheme.typography.displayMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        // You can add additional information or UI elements here
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
            text = description,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(descriptionTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .paddingFromBaseline(top = 32.dp)
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
fun ComicsSection(comicListItems: List<ModelComicsSuperHeroList>) {
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
fun ComicsList(comicListItems: List<ModelComicsSuperHeroList>) {

    Column {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        fun Date.toStringFormatted(): String {
            return outputDateFormat.format(this)
        }
        LazyRow(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraMedium)) {
            items(comicListItems) { item ->
                for (i in 0 until item.data?.results?.size!!) {
                    val comicImage = item.data.results[i].image
                    val comicTitle = item.data.results[i].title
//                    val comicDate =
//                        item.data.results[i].dates?.get(0)?.date.let { inputDateFormat.parse(it) }
//                            ?.toStringFormatted()

                    ComicCard(
                        item = item,
                        comicImage = comicImage.toString(),
                        comicTitle = comicTitle.toString(),
                        comicDate = ""
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ComicCard(
    item: ModelComicsSuperHeroList,
    comicImage: String,
    comicTitle: String,
    comicDate: String
) {
    Box(
        modifier = Modifier
            .height(220.dp)
            .width(170.dp)
            .padding(end = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(
            model = comicImage,
            contentDescription =
            stringResource(
                id = R.string.photo_content_description,
                comicTitle
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
            text = comicTitle,
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
            text = comicDate,
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
fun SuperheroScreenPreview() {
    SuperheroScreen(
//        viewModel = mockViewModel,
        superheroId = 1011334
    )
}
