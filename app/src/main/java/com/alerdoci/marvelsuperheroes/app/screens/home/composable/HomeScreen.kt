package com.alerdoci.marvelsuperheroes.app.screens.home.composable

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.common.extensions.ModifierExtensions.noRippleClickable
import com.alerdoci.marvelsuperheroes.app.common.states.ResourceState
import com.alerdoci.marvelsuperheroes.app.common.states.error.ErrorScreen
import com.alerdoci.marvelsuperheroes.app.common.states.loading.LoadingScreen
import com.alerdoci.marvelsuperheroes.app.common.utils.ThemeMode
import com.alerdoci.marvelsuperheroes.app.components.DiagonalDivider
import com.alerdoci.marvelsuperheroes.app.components.InfoDialog
import com.alerdoci.marvelsuperheroes.app.screens.home.viewmodel.HomeViewModel
import com.alerdoci.marvelsuperheroes.app.screens.home.viewmodel.marvelSuperHeroMock1
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.amber_A100
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.blue_grey_900
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.grey_500
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.orange_A200
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.red_800
import com.alerdoci.marvelsuperheroes.app.theme.dimens
import com.alerdoci.marvelsuperheroes.app.theme.spacing
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import me.saket.telephoto.zoomable.rememberZoomableState
import me.saket.telephoto.zoomable.zoomable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    onItemClick: (superHeroId: Int) -> Unit
) {
    val infoDialog = remember { mutableStateOf(false) }

    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()

    val systemUiController = rememberSystemUiController()

    systemUiController.setStatusBarColor(
        color = MaterialTheme.colorScheme.background,
        darkIcons = viewModel.getCurrentTheme() == ThemeMode.Light
    )

    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colorScheme.background,
        darkIcons = viewModel.getCurrentTheme() == ThemeMode.Light
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        val superHeroListState by viewModel.superHeroes.collectAsState()
        val superHeroListPagingState =
            viewModel.getMarvelSuperHeroesPager().collectAsLazyPagingItems()

        val superHeroSearchedState by viewModel.superHeroSearched.collectAsState()
        var superHeroSearchedText: String = ""

        var textSearched by remember { mutableStateOf("") }
        var textActive by remember { mutableStateOf(false) }
        val context = LocalContext.current
        val snackbarHostState = remember { SnackbarHostState() }

        var clickCount = 0

        val marvelTitle by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_marvel_title))
        val themeMode = ThemeMode.values()[viewModel.getThemeValue()]

        val iconRes = if (themeMode == ThemeMode.Dark) {
            R.drawable.ic_sun
        } else {
            R.drawable.ic_moon
        }

        val contentColor = if (themeMode == ThemeMode.Dark) amber_A100 else blue_grey_900

        Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(red_800)
                        .padding(top = MaterialTheme.spacing.extraMedium),
                    Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                infoDialog.value = true
                            },
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .fillMaxHeight(),
                        ) {
                            Icon(
                                Icons.Outlined.Info,
                                contentDescription = "App info",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(27.dp),
                            )
                        }
                        LottieAnimation(
                            composition = marvelTitle,
                            modifier = Modifier
                                .noRippleClickable {
                                    clickCount++
                                    if (clickCount == 5) {
                                        clickCount = 0
                                        isSheetOpen = true
                                    }
                                }
                                .weight(1f)
                                .padding(horizontal = 80.dp)
                        )
                        IconButton(
                            onClick = {
                                val newTheme =
                                    if (themeMode == ThemeMode.Dark) ThemeMode.Light else ThemeMode.Dark
                                viewModel.setTheme(newTheme)
                            },
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .fillMaxHeight(),
                        ) {
                            Icon(
                                painterResource(id = iconRes),
                                contentDescription = "Theme Toggle",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(25.dp),
                            )
                        } /*
                            ImageSwitch(
                                checkedImage = painterResource(R.drawable.good_night),
                                unCheckedImage = painterResource(R.drawable.good_morning),
                                size = 50.dp,
                                checked = themeMode == ThemeMode.Dark,
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        viewModel.setTheme(ThemeMode.Dark)
                                    } else {
                                        viewModel.setTheme(ThemeMode.Light)
                                    }
                                }
                            )
                            Switch(
                                checked = themeMode == ThemeMode.Dark,
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        viewModel.setTheme(ThemeMode.Dark)
                                    } else {
                                        viewModel.setTheme(ThemeMode.Light)
                                    }
                                },
                                thumbContent = {
                                    Icon(
                                        modifier = Modifier
                                            .size(SwitchDefaults.IconSize),
                                        imageVector = if (themeMode == ThemeMode.Dark) Icons.Rounded.DarkMode else Icons.Rounded.LightMode,
                                        contentDescription = "Switch Icon"
                                    )
                                },
                                colors = SwitchDefaults.colors(
                                    //Track = background, Thumb = circle,
                                    checkedTrackColor = blue_grey_900,
                                    checkedThumbColor = blue_grey_500,
                                    uncheckedTrackColor = orange_300,
                                    uncheckedThumbColor = blue_grey_300,
                                    uncheckedIconColor = Color.White,
                                    uncheckedBorderColor = Color.Transparent
                                ),
                            ) */
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = CenterHorizontally,
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
                                    "Search Bar not implemented yet :( \n Item searched: $textSearched",
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
                            //Not implemented yet
//                            if (textSearched.isNotEmpty()) {
//                            launch getSuperHeroSearched(textSearched)
//                            else
//                            launch getMarvelSuperHeroesPager()
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
                            var items by rememberSaveable {
                                mutableStateOf<List<ModelResult>>(
                                    emptyList()
                                )
                            }
                            val scrollState = rememberScrollState()
                            val lazyState = rememberLazyListState()
                            val snapBehavior =
                                rememberSnapFlingBehavior(lazyListState = lazyState)
                            when (superHeroListState) {
                                is ResourceState.Loading -> Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = CenterHorizontally,
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
                                    LazyColumn(
                                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.tiny),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .zIndex(1f),
                                        state = lazyState,
                                        flingBehavior = snapBehavior
                                    ) {
                                        item {
                                            Spacer(modifier = Modifier.height(if (scrollState.isScrollInProgress) MaterialTheme.dimens.custom0 else MaterialTheme.dimens.custom40))
                                        }
                                        items(
                                            superHeroListPagingState,
                                            key = { kSuperHero: ModelResult -> kSuperHero.id!! }) { superHeroItem ->
                                            println("superHeroItem: ${superHeroItem?.name}")
                                            if (superHeroItem != null) {
                                                SuperheroItem(
                                                    superHero = superHeroItem,
                                                    onItemClick = onItemClick,
                                                    modifier = Modifier.animateItemPlacement(),
                                                )
                                            }
                                        }
                                    }

                                else -> {}
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
    if (infoDialog.value) {
        InfoDialog(
            title = stringResource(R.string.dialog_title),
            desc = stringResource(R.string.dialog_description),
            buttonText = stringResource(R.string.dialog_button),
            onDismiss = {
                infoDialog.value = false
            }
        )
    }
    if (isSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                isSheetOpen = false
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp),
                horizontalAlignment = CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.modal_title),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .drawBehind {
                            val verticalOffset = size.height
                            drawLine(
                                color = orange_A200,
                                strokeWidth = 2.dp.toPx(),
                                start = Offset(0f, verticalOffset),
                                end = Offset(size.width, verticalOffset)
                            )
                        },
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    text = stringResource(R.string.modal_subtitle),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = stringResource(R.string.modal_body),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Image(
                    painter = painterResource(id = R.drawable.cheems),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                        .clip(RoundedCornerShape(15.dp))
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SuperheroItem(
    superHero: ModelResult,
    modifier: Modifier,
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
        ) {
            Box(
                modifier = Modifier.fillMaxHeight()
            ) {
                SubcomposeAsyncImage(
                    model = superHero.imageFinal,
                    contentDescription = stringResource(
                        id = R.string.photo_content_description,
                        superHero.name.orEmpty()
                    ),
                    loading = { },
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .blur(MaterialTheme.dimens.custom20)
                        .aspectRatio(1 / 1f),
                )
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(superHero.imageFinal)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(
                        id = R.string.photo_content_description,
                        superHero.name.orEmpty()
                    ),
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
                        .padding(all = MaterialTheme.spacing.xMedium)
                        .zoomable(
                            rememberZoomableState(),
                            onClick = { superHero.id?.let { onItemClick(it) } })
                        .clip(CutCornerShape(topStart = MaterialTheme.spacing.extraMedium))
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
                    modifier = Modifier
                        .basicMarquee(
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
                        horizontalAlignment = CenterHorizontally,
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
                    VerticalDivider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = MaterialTheme.dimens.custom30)
                            .alpha(0.6f),
                        thickness = MaterialTheme.dimens.custom1,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Column(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.semiSmall),
                        horizontalAlignment = CenterHorizontally,
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
                    VerticalDivider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = MaterialTheme.dimens.custom30)
                            .alpha(0.6f),
                        thickness = MaterialTheme.dimens.custom1,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Column(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.semiSmall),
                        horizontalAlignment = CenterHorizontally,
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
    HorizontalDivider(
        modifier = Modifier
            .padding(top = MaterialTheme.spacing.tiny)
            .alpha(0.3f),
        thickness = MaterialTheme.dimens.custom1,
        color = grey_500
    )
}

@Preview("Light Theme", showBackground = true)
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SuperheroItemPreview() {
    SuperheroItem(
        superHero = marvelSuperHeroMock1,
        modifier = Modifier,
        onItemClick = {})
}
