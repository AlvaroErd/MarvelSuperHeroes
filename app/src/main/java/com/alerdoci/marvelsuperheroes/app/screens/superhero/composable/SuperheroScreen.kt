package com.alerdoci.marvelsuperheroes.app.screens.superhero.composable

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ZoomOutMap
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.SubcomposeAsyncImage
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.common.states.ResourceState
import com.alerdoci.marvelsuperheroes.app.common.states.error.ErrorScreen
import com.alerdoci.marvelsuperheroes.app.common.states.loading.LoadingScreen
import com.alerdoci.marvelsuperheroes.app.components.DiagonalDivider
import com.alerdoci.marvelsuperheroes.app.screens.superhero.viewmodel.SuperHeroViewModel
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.amber_400
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.orange_A200
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.red_800
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.transparent
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.white
import com.alerdoci.marvelsuperheroes.app.theme.dimens
import com.alerdoci.marvelsuperheroes.app.theme.spacing
import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.IMAGE_NOT_FOUND
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsResult
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.mock.marvelSuperHeroComicMock1
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import me.saket.telephoto.zoomable.rememberZoomableState
import me.saket.telephoto.zoomable.zoomable
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SuperheroScreen(
    superHeroViewModel: SuperHeroViewModel = hiltViewModel(),
    superheroId: Int
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val superHeroState by superHeroViewModel.currentSuperHero.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val superHeroComicListPagingState: LazyPagingItems<ModelComicsResult> =
        superHeroViewModel.loadSuperHeroComic.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        superHeroViewModel.loadSuperHero(superheroId)
    }

    println(superheroId)
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        val superHeroDetail = superHeroState
        val superHeroComic = superHeroComicListPagingState
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            when (superHeroDetail) {
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
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        val superHeroes = superHeroDetail.data as List<ModelResult>
                        val superHero = superHeroes[0]
                        HeaderSection(
                            modifier = Modifier,
                            title = superHero.name!!,
                            image = superHero.image!!
                        )
                        Column {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 16.dp)
                            ) {

                                Text(
                                    text = stringResource(id = R.string.description),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
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

                                if (superHero.description!!.isEmpty()) {
                                    Text(
                                        text = stringResource(id = R.string.description_not_available_description),
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier
                                            .paddingFromBaseline(top = 20.dp)
                                    )
                                } else {
                                    SelectionContainer(
                                        modifier = Modifier
                                            .paddingFromBaseline(top = 20.dp)
                                    ) {
                                        Text(
                                            text = superHero.description,
                                            style = MaterialTheme.typography.bodyLarge,
                                        )
                                    }
                                }
                                if (superHero.image != IMAGE_NOT_FOUND) {
                                    OutlinedButton(
                                        onClick = { showDialog = true },
                                        modifier = Modifier
                                            .padding(horizontal = 30.dp)
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp)
                                            .padding(top = 16.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = transparent,
                                            contentColor = amber_400
                                        ),
                                        border = BorderStroke(2.dp, amber_400),
                                        shape = CutCornerShape(
                                            topStart = MaterialTheme.dimens.custom20,
                                            bottomEnd = MaterialTheme.dimens.custom20
                                        ),
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.ZoomOutMap,
                                            contentDescription = ""
                                        )
                                        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                                        Text(text = "See full image")
                                    }
                                }
                            }
                        }
                        Column(
                            modifier = Modifier
                                .padding(horizontal = MaterialTheme.spacing.medium)
                        ) {
                            Column {
                                when (superHeroComic.loadState.refresh) {
                                    is LoadState.Loading -> {
                                        Box(modifier = Modifier.height(300.dp)) {
                                            LoadingScreen()
                                        }
                                    }

                                    is LoadState.NotLoading -> {
                                        if (superHero.comics != 0) {
                                            Text(
                                                text = stringResource(id = R.string.comics_appear),
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier
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
                                        }
                                        else {
                                            Text(
                                                text = stringResource(id = R.string.comics_appear),
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier
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
                                                text = stringResource(id = R.string.no_recent_comics),
                                                style = MaterialTheme.typography.bodyLarge,
                                                modifier = Modifier
                                                    .paddingFromBaseline(top = 20.dp)
                                            )
                                        }
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(top = 16.dp)
                                        ) {
                                            Column {
                                                LazyRow(
                                                    horizontalArrangement = Arrangement.spacedBy(
                                                        MaterialTheme.spacing.tiny
                                                    ),
                                                ) {
                                                    items(
                                                        count = superHeroComic.itemCount,
                                                        key = superHeroComic.itemKey { superhero -> superhero.id }
                                                    ) { superHeroItem ->
                                                        superHeroComic[superHeroItem]?.let { item ->
                                                            ComicCard(
                                                                item = item
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(40.dp))
                                    }

                                    is LoadState.Error -> {
                                        ErrorScreen()
                                    }
                                }
                            }

                        }

                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = { showDialog = false },

                                content = {
                                    (LocalView.current.parent as DialogWindowProvider)
                                        .window.setDimAmount(.7f)
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.padding(vertical = 40.dp)
                                    ) {
                                        Text(
                                            text = stringResource(R.string.zoom_in),
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.displaySmall,
                                            color = white,
                                        )
                                        SubcomposeAsyncImage(
                                            model = superHero.image,
                                            contentDescription = "",
                                            contentScale = ContentScale.FillWidth,
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(vertical = 10.dp)
                                                .zoomable(
                                                    rememberZoomableState()
                                                )
                                        )
                                            OutlinedButton(
                                                onClick = { showDialog = false },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = MaterialTheme.colorScheme.background,
                                                ),
                                                border = BorderStroke(
                                                    2.dp,
                                                    MaterialTheme.colorScheme.onBackground
                                                )
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Filled.Close,
                                                    contentDescription = "",
                                                    tint = MaterialTheme.colorScheme.onBackground,
                                                    modifier = Modifier.size(30.dp)
                                                )
                                            }
                                    }

                                }
                            )
                        }
//                    }
                    }

                ResourceState.Idle -> {
                    ErrorScreen()
                }
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
    image: String,
    modifier: Modifier
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
            SelectionContainer(modifier = Modifier
                .zIndex(1f)
                .constrainAs(characterName) {
                    bottom.linkTo(characterImage.bottom)
                    start.linkTo(characterImage.start)
                    end.linkTo(characterImage.end)
                }
                .paddingFromBaseline(bottom = 40.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White,
                )
            }
        }
    }
}

@Composable
fun MarvelAttribution() {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(Color.Red)
    ) {
        Text(
            text = stringResource(R.string.copyright_marvel, currentYear),
            color = Color.White,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp)
        )
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
            .width(170.dp),
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
