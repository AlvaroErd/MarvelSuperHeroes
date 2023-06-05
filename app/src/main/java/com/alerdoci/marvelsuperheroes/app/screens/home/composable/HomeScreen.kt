package com.alerdoci.marvelsuperheroes.app.screens.home.composable

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.common.states.ResourceState
import com.alerdoci.marvelsuperheroes.app.common.states.error.ErrorScreen
import com.alerdoci.marvelsuperheroes.app.common.states.loading.LoadingScreen
import com.alerdoci.marvelsuperheroes.app.components.DiagonalDivider
import com.alerdoci.marvelsuperheroes.app.screens.home.viewmodel.HomeViewModel
import com.alerdoci.marvelsuperheroes.app.screens.home.viewmodel.marvelSuperHeroMock1
import com.alerdoci.marvelsuperheroes.app.theme.dimens
import com.alerdoci.marvelsuperheroes.app.theme.grey_500
import com.alerdoci.marvelsuperheroes.app.theme.orange_A200
import com.alerdoci.marvelsuperheroes.app.theme.red_800
import com.alerdoci.marvelsuperheroes.app.theme.spacing
import com.alerdoci.marvelsuperheroes.domain.models.features.superheroes.ModelResult

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    onItemClick: (superHeroId: Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        val superHeroListState by viewModel.superHeroes.collectAsStateWithLifecycle()
        var textSearched by remember { mutableStateOf("") }
        var textActive by remember { mutableStateOf(false) }
        val context = LocalContext.current
        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            )
            {
                val marvelTitle by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_marvel_title))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .padding(paddingValues = paddingValues)
                        .background(red_800)
                        .padding(top = MaterialTheme.spacing.extraMedium),
                    Arrangement.Center
                ) {
                    LottieAnimation(
                        composition = marvelTitle,
                        modifier = Modifier
                            .height(MaterialTheme.dimens.custom50)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        modifier = Modifier
                            .background(red_800)
                            .fillMaxWidth()
                            .padding(
                                top = MaterialTheme.spacing.tiny,
                            ),
                        Alignment.Center
                    ) {
                        SearchBar(
                            query = textSearched,
                            onQueryChange = { newTextSearched ->
                                textSearched =
                                    newTextSearched
                            },
                            onSearch = {
                                textActive = false
//                                textSearched = ""
                                Toast.makeText(
                                    context,
                                    "Item searched: $textSearched",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            },
                            active = textActive,
                            onActiveChange = {
                                textActive = it
                            },
                            placeholder = { Text(text = stringResource(R.string.search_superhero)) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search icon"
                                )
                            },
                            colors = SearchBarDefaults.colors(dividerColor = red_800),
                            trailingIcon = {
                                if (textActive) {
                                    IconButton(onClick = {
                                        if (textSearched.isNotEmpty()) {
                                            textSearched = ""
                                        } else {
                                            textActive = false
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Close icon",
                                        )
                                    }
                                }
                            },
                            modifier = Modifier.padding(
                                bottom = MaterialTheme.spacing.tiny
                            )
                        )
                        {
//                    if (textSearched.isNotEmpty()) {
//                        val filteredSuperhero =
//                            ((superHeroListState as ResourceState.Success).data as List<ModelResult>).filter {
//                                it.contains(
//                                    textSearched,
//                                    true
//                                ) as List
//                            }
//                        LazyColumn {
//                            items(filteredSuperhero) {
//                                Text(text = textSearched, modifier = Modifier.clickable {
//                                    Toast.makeText(
//                                        context,
//                                        "Item clicked: $textSearched",
//                                        Toast.LENGTH_LONG
//                                    ).show()
//                                })
//                            }
//                        }
//                    }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                    {
                        DiagonalDivider(
                            modifier = Modifier
                                .height(40.dp)
                                .zIndex(1f)
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = MaterialTheme.spacing.xMedium)
                        )
                        {
                            val superHeroListState by viewModel.superHeroes.collectAsStateWithLifecycle()
                            var items by rememberSaveable {
                                mutableStateOf<List<ModelResult>>(
                                    emptyList()
                                )
                            }
                            val scrollState = rememberScrollState()
                            val lazyState = rememberLazyListState()

                            when (superHeroListState) {
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

                                is ResourceState.Success -> items =
                                    (superHeroListState as ResourceState.Success).data as List<ModelResult>

                                else -> {}
                            }
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.tiny),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .zIndex(1f),
                                state = lazyState
                            ) {
                                item {
                                    Spacer(modifier = Modifier.height(if (scrollState.isScrollInProgress) MaterialTheme.dimens.custom0 else MaterialTheme.dimens.custom40))
                                }
                                items(items) { superHeroItem ->
                                    println("superHeroItem: ${superHeroItem.name}")
                                    SuperheroItem(
                                        superHero = superHeroItem,
                                        onItemClick = onItemClick
                                    )

                                    if (items.indexOf(superHeroItem) < items.lastIndex)
                                        Divider(
                                            color = grey_500,
                                            thickness = MaterialTheme.dimens.custom1,
                                            modifier = Modifier
                                                .padding(top = MaterialTheme.spacing.tiny)
                                                .alpha(0.3f)
                                        )
                                    else {
                                        Spacer(modifier = Modifier.padding(bottom = (MaterialTheme.dimens.custom50)))
                                    }
                                }
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            Arrangement.Bottom,
                        ) {
                            DiagonalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .rotate(180f)
                            )
                        }
                    }

                }

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SuperheroItem(
    superHero: ModelResult,
    modifier: Modifier = Modifier,
    onItemClick: (superHeroId: Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimens.custom150)
            .clip(
                CutCornerShape(
                    topStart = MaterialTheme.dimens.custom20,
                    bottomEnd = MaterialTheme.dimens.custom20
                )
            )
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable {
                superHero.id?.let { onItemClick(it) }
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
//                .shadow(10.dp)
        ) {
//            val imageLoader = ImageLoader(LocalContext.current)
//            val imageRequest = ImageRequest.Builder(LocalContext.current).data(superHero.thumbnail?.path + superHero.thumbnail?.extension)
//                .crossfade(true)
//                .build()
//            val painter = rememberAsyncImagePainter(model = imageRequest)
//            val painterState = painter.state
//            Image(
//                painter = painter,
//                contentDescription = superHero.name,
//                modifier = Modifier
//                    .size(120.dp)
//                    .align(Alignment.CenterVertically)
//            )
//            if (painterState is AsyncImagePainter.State.Loading) {
//                Box(contentAlignment = Alignment.Center) {
//                    Image(
//                        painter = painterResource(id = R.drawable.groot_placeholder),
//                        contentDescription = "",
//                        modifier = Modifier.fillMaxSize()
//                    )
//                }
//            }
            Box(
                modifier = Modifier.fillMaxHeight()
            ) {
                SubcomposeAsyncImage(
                    model = superHero.imageFinal,
                    contentDescription = stringResource(
                        id = R.string.photo_content_description,
                        superHero.name.orEmpty()
                    ),
                    loading = {
                        CircularProgressIndicator(
                            color = red_800,
                            modifier = Modifier.size(MaterialTheme.dimens.custom30)
                        )
                    },
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .blur(MaterialTheme.dimens.custom20)
                        .aspectRatio(1 / 1f),
                )
                SubcomposeAsyncImage(
                    model = superHero.imageFinal,
                    contentDescription = stringResource(
                        id = R.string.photo_content_description,
                        superHero.name.orEmpty()
                    ),
                    loading = { CircularProgressIndicator(color = red_800) },
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(all = MaterialTheme.spacing.xMedium)
                        .clip(CutCornerShape(topStart = MaterialTheme.spacing.extraMedium))
//                        .clickable {
//                            superHero.id?.let { onItemClick(it) }
//                        }
                        .aspectRatio(1 / 1f),
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.custom10),
                Arrangement.Center
            ) {
                Text(
                    text = superHero.name.orEmpty(),
                    maxLines = 2,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier
                        .padding(bottom = MaterialTheme.spacing.small)
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
                    text = if (superHero.description.isNullOrBlank()) stringResource(R.string.description_not_available) else superHero.description.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.basicMarquee(
                        iterations = Int.MAX_VALUE,
                        delayMillis = 0,
                        initialDelayMillis = 3000
                    )
                )
                Row(
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.spacing.semiSmall)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.semiSmall),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center

                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_event),
                            contentDescription = stringResource(R.string.events_icon),
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .height(MaterialTheme.dimens.custom36)
                                .padding(vertical = MaterialTheme.spacing.semiSmall)
                        )
                        Text(
                            text = pluralStringResource(
                                id = R.plurals.events,
                                count = superHero.events?.available ?: 1,
                                superHero.events?.available ?: 1
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = MaterialTheme.dimens.custom30)
                            .width(MaterialTheme.dimens.custom1)
                            .background(MaterialTheme.colorScheme.onBackground)
                            .alpha(0.6f)
                    )
                    Column(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.semiSmall),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_comic),
                            contentDescription = stringResource(R.string.comic_icon),
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .height(MaterialTheme.dimens.custom36)
                                .padding(vertical = MaterialTheme.spacing.semiSmall)
                        )
                        Text(
                            text = pluralStringResource(
                                id = R.plurals.comics,
                                count = superHero.comics?.available ?: 1,
                                superHero.comics?.available ?: 1
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = MaterialTheme.dimens.custom30)
                            .width(MaterialTheme.dimens.custom1)
                            .background(MaterialTheme.colorScheme.onBackground)
                            .alpha(0.6f)
                    )
                    Column(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.semiSmall),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_serie),
                            contentDescription = stringResource(R.string.series_icon),
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .height(MaterialTheme.dimens.custom36)
                                .padding(vertical = MaterialTheme.spacing.semiSmall)
                        )
                        Text(
                            text = pluralStringResource(
                                id = R.plurals.series,
                                count = superHero.series?.available ?: 1,
                                superHero.series?.available ?: 1
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}

//
//@Preview("Light Theme", showBackground = true)
//@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    val navController = rememberNavController()
//    HomeScreen(navController = navController)
//}

@Preview("Light Theme", showBackground = true)
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SuperheroItemPreview() {
    SuperheroItem(superHero = marvelSuperHeroMock1, onItemClick = {})
}