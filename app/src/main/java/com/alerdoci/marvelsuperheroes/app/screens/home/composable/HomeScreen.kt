package com.alerdoci.marvelsuperheroes.app.screens.home.composable

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.annotation.FloatRange
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
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
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.graphics.ColorUtils
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
import com.alerdoci.marvelsuperheroes.app.common.utils.ToastDuration
import com.alerdoci.marvelsuperheroes.app.common.utils.ToastHostState
import com.alerdoci.marvelsuperheroes.app.components.AnimatedPlaceholder
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
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Search
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.saket.telephoto.zoomable.rememberZoomableState
import me.saket.telephoto.zoomable.zoomable
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.compose.OnParticleSystemUpdateListener
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.PartySystem
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Spread
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    onItemClick: (superHeroId: Int) -> Unit
) {
    val infoDialog = remember { mutableStateOf(false) }
    var showConfetti by remember { mutableStateOf(false) }

    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()

    val systemUiController = rememberSystemUiController()
    val infiniteTransition = rememberInfiniteTransition(label = "")
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

        Toasty.Config.getInstance()
            .tintIcon(true) // optional (apply textColor also to the icon)
//            .setToastTypeface(Typeface.DEFAULT) // optional
//            .setTextSize(28) // optional
            .allowQueue(false) // optional (prevents several Toastys from queuing)
//            .setGravity(int gravity, int xOffset, int yOffset) // optional (set toast gravity, offsets are optional)
            .supportDarkTheme(true) // optional (whether to support dark theme or not)
            .setRTL(false) // optional (icon is on the right)
            .apply(); // required

        val superHeroListState by viewModel.superHeroes.collectAsState()
        val superHeroListPagingState =
            viewModel.getMarvelSuperHeroesPager().collectAsLazyPagingItems()

        val superHeroSearchedState by viewModel.superHeroSearched.collectAsState()
        var superHeroSearchedText: String = ""

        var textSearched by remember { mutableStateOf("") }
        var textActive by remember { mutableStateOf(false) }
        val context = LocalContext.current
        val applicationContext = context.applicationContext
        val snackbarHostState = remember { SnackbarHostState() }

        var clickCount = 0

        val marvelTitle by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_marvel_title))
        val themeMode = ThemeMode.values()[viewModel.getThemeValue()]

        val iconRes = if (themeMode == ThemeMode.Dark) {
            R.drawable.ic_sun
        } else {
            R.drawable.ic_moon
        }

        var successToast by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val toastHostState = remember { ToastHostState() }

        val contentColor = if (themeMode == ThemeMode.Dark) amber_A100 else blue_grey_900

        var scaleState by remember { mutableFloatStateOf(1f) }
        val scale by animateFloatAsState(scaleState, label = "")

        val scrollState = rememberScrollState()
        val lazyState = rememberLazyListState()
        val savedList = rememberSaveable(saver = LazyListState.Saver) {
            lazyState
        }
        val snapBehavior = rememberSnapFlingBehavior(lazyListState = lazyState)

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
                            Image(
                                modifier = Modifier.size(27.dp),
                                painter = painterResource(id = R.drawable.ic_info),
                                contentDescription = ""
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
//                        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                        IconButton(
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .fillMaxHeight(),
                            onClick = {
                                val newTheme =
                                    if (themeMode == ThemeMode.Dark) ThemeMode.Light else ThemeMode.Dark
                                viewModel.setTheme(newTheme)
                            },
                        )
                        {
                            Crossfade(
                                targetState = themeMode,
                                label = "",
                                animationSpec = tween(450)
                            ) { currentTheme ->
                                when (currentTheme) {
                                    ThemeMode.Dark ->
                                        Icon(
                                            painterResource(id = R.drawable.ic_moon),
                                            contentDescription = "Theme Toggle",
                                            tint = Color.White,
                                            modifier = Modifier
                                                .size(25.dp),
                                        )

                                    ThemeMode.Light ->
                                        Icon(
                                            painterResource(id = R.drawable.ic_sun),
                                            contentDescription = "Theme Toggle",
                                            tint = Color.White,
                                            modifier = Modifier
                                                .size(25.dp),
                                        )

                                    else -> {}
                                }
                            }
//                            }
//
//                            PulsatingHeartIcon(infiniteTransition)
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
//                                textSearched = ""
                                textActive = false

//                                context.customMDToast(
//                                    message = "Search Bar not implemented yet :( \n Item searched: $textSearched",
//                                    duration = LENGTH_SHORT,
//                                    type = TYPE_WARNING,
//                                    bgColor = null,
//                                    icon = ,
//                                    borderRadius = null,
//                                    elevation = null,
//                                )
//                                Toasty.custom(
//                                    context,
//                                    "This is a Custom toast.",
//                                    R.drawable.ic_sun,
//                                    R.color.red_500,
//                                    3000,
//                                    true,
//                                    true
//                                ).show()
                            },
                            active = textActive,
                            onActiveChange = {
                                textActive = it
                            },
//                            placeholder = { Text(text = stringResource(R.string.search_superhero)) },
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
                            .clickable {
                                scope.launch {
                                    toastHostState.showToast(
                                        "Search Bar not implemented yet :(",
                                        "Item searched",
                                        EvaIcons.Outline.Search,
                                        ToastDuration.Infinite,
//                                        false
                                    )
                                }
                            }
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
                                        state = savedList,
                                        flingBehavior = snapBehavior
                                    ) {
                                        item {
                                            Spacer(modifier = Modifier.height(if (scrollState.isScrollInProgress) MaterialTheme.dimens.custom0 else MaterialTheme.dimens.custom40))
                                        }
                                        items(
                                            superHeroListPagingState,
                                            key = { kSuperHero: ModelResult -> kSuperHero.id }) { superHeroItem ->
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
    if (showConfetti) {
        val primary = MaterialTheme.colorScheme.primary
        KonfettiView(
            modifier = Modifier.fillMaxSize(),
            parties = remember { particles(primary) },
            updateListener = object : OnParticleSystemUpdateListener {
                override fun onParticleSystemEnded(system: PartySystem, activeSystems: Int) {
                    if (activeSystems == 0) showConfetti = false
                }
            }
        )
    }

    if (isSheetOpen) {
        showConfetti = true
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
                                count = superHero.events ?: 1,
                                superHero.events ?: 1
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
                                count = superHero.comics ?: 1,
                                superHero.comics ?: 1
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
                                count = superHero.series ?: 1,
                                superHero.series ?: 1
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


private fun particles(primary: Color) = listOf(
    Party(
        speed = 0f,
        maxSpeed = 15f,
        damping = 0.9f,
        angle = Angle.BOTTOM,
        spread = Spread.ROUND,
        colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def).map {
            it.blend(
                primary
            )
        },
        emitter = Emitter(duration = 2, TimeUnit.SECONDS).perSecond(100),
        position = Position.Relative(0.0, 0.0).between(Position.Relative(1.0, 0.0))
    ),
    Party(
        speed = 10f,
        maxSpeed = 30f,
        damping = 0.9f,
        angle = Angle.RIGHT - 45,
        spread = Spread.SMALL,
        colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def).map {
            it.blend(
                primary
            )
        },
        emitter = Emitter(duration = 2, TimeUnit.SECONDS).perSecond(30),
        position = Position.Relative(0.0, 1.0)
    ),
    Party(
        speed = 10f,
        maxSpeed = 30f,
        damping = 0.9f,
        angle = Angle.RIGHT - 135,
        spread = Spread.SMALL,
        colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def).map {
            it.blend(
                primary
            )
        },
        emitter = Emitter(duration = 2, TimeUnit.SECONDS).perSecond(30),
        position = Position.Relative(1.0, 1.0)
    )
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundedTextFieldColors(isError: Boolean): TextFieldColors =
    MaterialTheme.colorScheme.run {
        TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            cursorColor = if (isError) error else primary,
            focusedLabelColor = if (isError) error else primary,
            focusedLeadingIconColor = if (isError) error else onSurfaceVariant,
            unfocusedLeadingIconColor = if (isError) error else onSurfaceVariant,
            focusedTrailingIconColor = if (isError) error else onSurfaceVariant,
            unfocusedTrailingIconColor = if (isError) error else onSurfaceVariant,
            unfocusedLabelColor = if (isError) error else onSurfaceVariant,
            containerColor = if (isError) surfaceVariant.blend(error, 0.2f) else surfaceVariant,
        )
    }

fun Color.blend(
    color: Color,
    @FloatRange(from = 0.0, to = 1.0) fraction: Float = 0.2f
): Color = Color(ColorUtils.blendARGB(this.toArgb(), color.toArgb(), fraction))


fun Int.blend(
    color: Color,
    @FloatRange(from = 0.0, to = 1.0) fraction: Float = 0.2f
): Int = ColorUtils.blendARGB(this, color.toArgb(), fraction)


@Preview("Light Theme", showBackground = true)
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SuperheroItemPreview() {
    SuperheroItem(
        superHero = marvelSuperHeroMock1,
        modifier = Modifier,
        onItemClick = {})
}

@Composable
private fun PulsatingHeartIcon(infiniteTransition: InfiniteTransition) {
//1
    val pulsate by infiniteTransition.animateFloat(
        initialValue = 10f,
        targetValue = 60f,
        animationSpec = infiniteRepeatable(tween(1200), RepeatMode.Reverse), label = ""
    )
//2
    Icon(
        imageVector = Icons.Default.Favorite,
        contentDescription = "",
        modifier = Modifier
            .size(pulsate.dp)
            .offset(
                x = 10.dp, y = 10.dp
            )
    )

}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}

