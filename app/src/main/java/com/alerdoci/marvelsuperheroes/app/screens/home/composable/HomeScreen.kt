package com.alerdoci.marvelsuperheroes.app.screens.home.composable

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.common.extensions.Extensions.formatNumber
import com.alerdoci.marvelsuperheroes.app.common.extensions.ModifierExtensions.noRippleClickable
import com.alerdoci.marvelsuperheroes.app.common.states.error.ErrorScreen
import com.alerdoci.marvelsuperheroes.app.common.states.loading.LoadingScreen
import com.alerdoci.marvelsuperheroes.app.common.utils.ThemeMode
import com.alerdoci.marvelsuperheroes.app.components.AnimatedPlaceholder
import com.alerdoci.marvelsuperheroes.app.components.DiagonalDivider
import com.alerdoci.marvelsuperheroes.app.components.InfoDialog
import com.alerdoci.marvelsuperheroes.app.components.particles
import com.alerdoci.marvelsuperheroes.app.navigation.Screen
import com.alerdoci.marvelsuperheroes.app.screens.home.viewmodel.HomeViewModel
import com.alerdoci.marvelsuperheroes.app.screens.home.viewmodel.SettingsViewModel
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.grey_500
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.orange_A200
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.red_800
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.white
import com.alerdoci.marvelsuperheroes.app.theme.dimens
import com.alerdoci.marvelsuperheroes.app.theme.spacing
import com.alerdoci.marvelsuperheroes.model.features.superheroes.ModelResult
import com.alerdoci.marvelsuperheroes.model.features.superheroes.mock.marvelSuperHeroMock1
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import me.saket.telephoto.zoomable.rememberZoomableState
import me.saket.telephoto.zoomable.zoomable
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.compose.OnParticleSystemUpdateListener
import nl.dionsegijn.konfetti.core.PartySystem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel,
    searchQuery: String?,
    onSearchComplete: (String) -> Unit
) {
    val infoDialog = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    val isSystemInDarkTheme: Boolean = isSystemInDarkTheme()
    val currentTheme = settingsViewModel.getCurrentTheme(isSystemInDarkTheme)
    val systemUiController = rememberSystemUiController()

        systemUiController.setStatusBarColor(
            color = MaterialTheme.colorScheme.background,
            darkIcons = currentTheme == ThemeMode.Light
        )
        systemUiController.setNavigationBarColor(
            color = MaterialTheme.colorScheme.background,
            darkIcons = currentTheme == ThemeMode.Light
        )

    val displayDialog = remember { mutableStateOf(false) }
    val radioOptions = listOf("Light", "Dark", "System")
    val displayValue =
        when (settingsViewModel.getThemeValue()) {
            ThemeMode.Light -> "Light"
            ThemeMode.Dark -> "Dark"
            else -> "System"
        }
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(displayValue) }

    var showConfetti by remember { mutableStateOf(false) }
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    var confettiShownInitially by remember { mutableStateOf(false) }

    LaunchedEffect(isSheetOpen) {
        if (isSheetOpen && !confettiShownInitially) {
            showConfetti = true
            confettiShownInitially = true
        } else if (!isSheetOpen && confettiShownInitially) {
            confettiShownInitially = false
            showConfetti = false
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        val superHeroListPagingState: LazyPagingItems<ModelResult> =
            viewModel.allCharacters.collectAsLazyPagingItems()

        LaunchedEffect(searchQuery != "") {
            if (searchQuery != null) {
                viewModel.searchCharacters(searchQuery)
            }
        }

        val searchQuery by viewModel.searchQuery.collectAsState()
        var textActive by remember { mutableStateOf(false) }
        val snackbarHostState = remember { SnackbarHostState() }

        var clickCount = 0

        val marvelTitle by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_marvel_title))

        var scaleState by remember { mutableFloatStateOf(1f) }
        val scale by animateFloatAsState(scaleState, label = "")

        val scrollState = rememberScrollState()
        val lazyState = rememberLazyListState()
        val savedList = rememberSaveable(saver = LazyListState.Saver) {
            lazyState
        }
        val state = rememberPullToRefreshState()

        if (state.isRefreshing) {
            LaunchedEffect(true) {
                viewModel.searchCharacters(searchQuery)
                delay(500)
                state.endRefresh()
            }
        }

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            containerColor = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
                        Box(
                            Modifier
                                .padding(start = 30.dp)
                                .scale(scale)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onPress = {
                                            scaleState = 1.3f
                                            delay(200)
                                            tryAwaitRelease()
                                            infoDialog.value = true
                                            scaleState = 0.8f
                                            delay(200)
                                            scaleState = 1f
                                        }
                                    )
                                }
                        ) {
                            Icon(
                                modifier = Modifier.size(27.dp),
                                painter = painterResource(id = R.drawable.ic_info),
                                contentDescription = "",
                                tint = white
                            )
                        }
                        LottieAnimation(
                            composition = marvelTitle,
                            modifier = Modifier
                                .padding(start = 15.dp)
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
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .fillMaxHeight(),
                            onClick = {
                                displayDialog.value = true
                            },
                        )
                        {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_change_theme),
                                contentDescription = "",
                                modifier = Modifier.size(30.dp),
                                tint = white
                            )
                        }
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
                            .zIndex(4f)
                            .padding(
                                top = MaterialTheme.spacing.tiny,
                            ),
                        Alignment.Center
                    ) {

                        SearchBar(
                            query = searchQuery,
                            onQueryChange = { newTextSearched ->
                                if (textActive) {
                                    viewModel.searchCharacters(newTextSearched)
                                }
                            },
                            onSearch = {
                                textActive = false
                                superHeroListPagingState.refresh()
                                viewModel.searchCharacters(searchQuery)
                                onSearchComplete(searchQuery)
                            },
                            active = textActive,
                            onActiveChange = {
                                textActive = it
                            },
                            placeholder = {
                                val hints = listOf(
                                    stringResource(id = R.string.search_superhero),
                                    stringResource(id = R.string.search_superheroine),
                                    stringResource(id = R.string.search_supervillain),
                                    stringResource(id = R.string.search_monster)
                                )
                                AnimatedPlaceholder(
                                    hints = hints,
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search icon",
                                    tint = MaterialTheme.colorScheme.onBackground,
                                )
                            },
                            colors = SearchBarDefaults.colors(dividerColor = red_800),
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = {
                                        viewModel.searchCharacters("")
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Close icon",
                                            tint = MaterialTheme.colorScheme.onBackground,
                                        )
                                    }
                                }
                            },
                            modifier = Modifier.padding(
                                bottom = MaterialTheme.spacing.tiny,
                            )
                        )
                        {

                        }

                    }
                    Box()
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
                            when (superHeroListPagingState.loadState.refresh) {
                                is LoadState.Loading -> {
                                    LoadingScreen()
                                }

                                is LoadState.NotLoading -> {
                                    Box(Modifier.nestedScroll(state.nestedScrollConnection)) {
                                        LazyColumn(
                                            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.tiny),
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .zIndex(1f),
                                            state = savedList,
                                        ) {
                                            if (!state.isRefreshing) {
                                                item {
                                                    Spacer(modifier = Modifier.height(if (scrollState.isScrollInProgress) MaterialTheme.dimens.custom0 else MaterialTheme.dimens.custom40))
                                                }
                                                items(
                                                    count = superHeroListPagingState.itemCount,
                                                    key = superHeroListPagingState.itemKey { superhero -> superhero.id }
                                                ) { superHeroItem ->
                                                    superHeroListPagingState[superHeroItem]?.let { item ->
                                                        println("superHeroItem: ${item.name}")
                                                        SuperheroItem(
                                                            superHero = item,
                                                            modifier = Modifier.animateItemPlacement(),
                                                        ) {
                                                            navController.navigate(
                                                                route = Screen.Superhero.navigateWithId(
                                                                    item.id
                                                                )
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .zIndex(2f)
                                        )
                                        {
                                            val scaleFraction = if (state.isRefreshing) 1f else
                                                LinearOutSlowInEasing.transform(state.progress)
                                                    .coerceIn(0f, 1f)

                                            PullToRefreshContainer(
                                                state = state,
                                                modifier = Modifier
                                                    .align(Alignment.TopCenter)
                                                    .graphicsLayer(
                                                        scaleX = scaleFraction,
                                                        scaleY = scaleFraction
                                                    ),
                                            )
                                        }
                                    }

                                }

                                is LoadState.Error -> {
                                    ErrorScreen()
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
    if (showConfetti) {
        val primary = MaterialTheme.colorScheme.primary
        KonfettiView(
            modifier = Modifier.fillMaxSize(),
            parties = remember { particles(primary) },
            updateListener = object : OnParticleSystemUpdateListener {
                override fun onParticleSystemEnded(system: PartySystem, activeSystems: Int) {
                    if (activeSystems == 0 && isSheetOpen) {
                        showConfetti = false
                    }
                }
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
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = stringResource(R.string.modal_subtitle),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = stringResource(R.string.modal_body),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
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

    if (displayDialog.value) {
        AlertDialog(
            shape = CutCornerShape(topStart = 24.dp, bottomEnd = 24.dp),
            onDismissRequest = {
                displayDialog.value = false
            },
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            title = {
                Text(
                    text = stringResource(id = R.string.theme_dialog_title),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,

                    )
            }, text = {
                Column(
                    modifier = Modifier.selectableGroup(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    radioOptions.forEach { text ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = { onOptionSelected(text) },
                                    role = Role.RadioButton,
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = null,
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.surfaceTint,
                                    unselectedColor = MaterialTheme.colorScheme.surfaceTint.copy(
                                        alpha = 0.4f
                                    ),
                                    disabledSelectedColor = Color.Black,
                                    disabledUnselectedColor = Color.Black
                                ),
                            )
                            Text(
                                text = text,
                                modifier = Modifier.padding(start = 16.dp),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.9f),
                            )
                        }
                    }
                }
            }, confirmButton = {
                TextButton(onClick = {
                    displayDialog.value = false

                    when (selectedOption) {
                        "Light" -> {
                            settingsViewModel.setTheme(
                                ThemeMode.Light
                            )
                        }

                        "Dark" -> {
                            settingsViewModel.setTheme(
                                ThemeMode.Dark
                            )
                        }

                        "System" -> {
                            settingsViewModel.setTheme(
                                ThemeMode.Auto
                            )
                        }
                    }
                }) {
                    Text(
                        stringResource(id = R.string.theme_dialog_apply_button),
                        color = MaterialTheme.colorScheme.surfaceTint,
                    )
                }
            }, dismissButton = {
                TextButton(onClick = {
                    displayDialog.value = false
                }) {
                    Text(
                        stringResource(id = R.string.cancel),
                        color = MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.7f),
                    )
                }
            }
        )
    }

}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SuperheroItem(
    superHero: ModelResult,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimens.custom150)
            .clip(
                CutCornerShape(
                    topStart = MaterialTheme.dimens.custom20,
                    bottomEnd = MaterialTheme.dimens.custom20
                )
            )
            .background(MaterialTheme.colorScheme.surfaceVariant),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxHeight()
            ) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                    SubcomposeAsyncImage(
                        model = superHero.image,
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
                            .data(superHero.image)
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
                                onClick = { onClick() })
                            .clip(CutCornerShape(topStart = MaterialTheme.spacing.extraMedium))
                            .aspectRatio(1 / 1f),
                    )
                } else {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(superHero.image)
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
                            .zoomable(
                                rememberZoomableState(),
                                onClick = { onClick() })
                            .aspectRatio(1 / 1f),
                    )
                }
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
                    color = MaterialTheme.colorScheme.onBackground,
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
                    color = MaterialTheme.colorScheme.onBackground,
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
                        Row {
                            Text(
                                color = MaterialTheme.colorScheme.onBackground,
                                text = formatNumber(superHero.events ?: 1),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            Text(
                                color = MaterialTheme.colorScheme.onBackground,
                                text = pluralStringResource(
                                    id = R.plurals.events,
                                    count = superHero.events ?: 1,
                                    superHero.events ?: 1
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
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
                        Row {
                            Text(
                                color = MaterialTheme.colorScheme.onBackground,
                                text = formatNumber(superHero.comics ?: 1),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            Text(
                                text = pluralStringResource(
                                    id = R.plurals.comics,
                                    count = superHero.comics ?: 1,
                                    superHero.comics ?: 1
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        }
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
                        Row {
                            Text(
                                text = formatNumber(superHero.series ?: 1),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                            Text(
                                text = pluralStringResource(
                                    id = R.plurals.series,
                                    count = superHero.series ?: 1,
                                    superHero.series ?: 1
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        }
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
        onClick = { }
    )
}

