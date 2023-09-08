package com.alerdoci.marvelsuperheroes.app.components

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.sqrt

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CircleRevealPager(
    slides: List<DataOnBoarding>,
    navigate_to_login: () -> Unit = {}
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        slides.size
    }
    var offsetY by remember { mutableStateOf(0f) }
    val pageCount = slides.size
    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            modifier = Modifier
                .pointerInteropFilter {
                    offsetY = it.y
                    false
                }
                .background(Color.Black),
            state = pagerState,
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        val pageOffset = pagerState.offsetForPage(page)
                        translationX = size.width * pageOffset

                        val endOffset = pagerState.endOffsetForPage(page)

                        shadowElevation = 20f
                        shape = CirclePath(
                            progress = 1f - endOffset.absoluteValue,
                            origin = Offset(
                                // set the intial position, from where the reveal shouls start
                                size.width,
                                size.height,//set this to offsetY, to start the revel,at the point the user touched
                            )
                        )
                        clip = true

                        val absoluteOffset = pagerState.offsetForPage(page).absoluteValue
                        val scale = 1f + (absoluteOffset.absoluteValue * .4f)

                        scaleX = scale
                        scaleY = scale

                        val startOffset = pagerState.startOffsetForPage(page)
                        alpha = (2f - startOffset) / 2f

                    },
                contentAlignment = Alignment.Center,
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(slides[page].backgroundColor))
                Box(modifier = Modifier.fillMaxSize()) {
                    TopContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .offset(y = 30.dp)
                            .windowInsetsPadding(WindowInsets.statusBars),
                        navigate_to_login = { navigate_to_login() },
                        pagerState.currentPage
                    )


                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                            .offset(y = 10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            contentScale = ContentScale.FillWidth,
                            painter = painterResource(slides[page].illustration),
                            contentDescription = "slide ${page} illustrations"
                        )
                        LabelGroup(
                            slides[page],
                            Modifier
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(10.dp)
        ) {
            WormPageIndicator(
                modifier = Modifier.align(Alignment.BottomCenter),
                totalPages = pageCount,
                currentPage = pagerState.currentPage
            )
            ButtonWithIndicator(
                modifier = Modifier.align(Alignment.BottomEnd),
                pagerState = pagerState,
                nav_to_login = navigate_to_login
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ButtonWithIndicator(
    modifier: Modifier,
    pagerState: PagerState,
    color: Color = Color(0xFFFAF4F4),
    nav_to_login: () -> Unit = {}
) {
    val pageInd = pagerState.currentPage
    val scope = rememberCoroutineScope()
    Box(
        modifier = modifier.size(70.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(color),

        ) {

        //left top corner
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(width = 35.dp, height = 42.dp)
                .padding(all = 3.dp)
                .clip(RoundedCornerShape(topStart = 22.dp))
                .background(
                    if (pageInd >= 0) Color.Yellow else Color.Gray
                )
        ) {}

        //right top corner

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(width = 35.dp, height = 42.dp)
                .padding(all = 3.dp)
                .clip(RoundedCornerShape(topEnd = 22.dp))
                .background(
                    if (pageInd >= 1) Color.Cyan else Color.LightGray
                )
        ) {}

        // bottom Curve
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(width = 70.dp, height = 28.dp)
                .padding(all = 3.dp)
                .clip(RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp))
                .background(
                    if (pageInd == 2) Color.Red else Color.LightGray
                )
        ) {}


        IconButton(
            onClick = {
                if (pageInd < 2)
                    scope.launch { pagerState.animateScrollToPage(pageInd + 1) }
                else nav_to_login()
            },
            modifier = Modifier
                .matchParentSize()
                .align(Alignment.Center)

                .padding(6.dp)
                .clip(RoundedCornerShape(10.dp)),
            colors = IconButtonDefaults.iconButtonColors(containerColor = color),
            interactionSource = remember { MutableInteractionSource() }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowForwardIos,
                tint = Color.Black,
                contentDescription = null
            )
        }
    }
}


class CirclePath(private val progress: Float, private val origin: Offset = Offset(0f, 0f)) : Shape {
    override fun createOutline(
        size: Size, layoutDirection: LayoutDirection, density: Density
    ): Outline {

        val center = Offset(
            x = size.center.x - ((size.center.x - origin.x) * (1f - progress)),
            y = size.center.y - ((size.center.y - origin.y) * (1f - progress)),
        )
        val radius = (sqrt(
            size.height * size.height + size.width * size.width
        ) * .5f) * progress

        return Outline.Generic(Path().apply {
            addOval(
                Rect(
                    center = center,
                    radius = radius,
                )
            )
        })
    }
}

data class DataOnBoarding(
    val label: String,
    val subLabel: String,
    val slideind: Int,
    val illustration: Int,
    val backgroundColor: Color,
)

// replace illustration according to slide
val onBoardingSlides = listOf(

    DataOnBoarding(
        label = "Welcome to BloomShows",
        subLabel = "Step into a World of Cinematic Delights",
        slideind = 0,
        illustration = R.drawable.groot_placeholder,
        backgroundColor = MarvelColors.amber_500
    ),
    DataOnBoarding(
        label = "Discover Movies and Showtimes",
        subLabel = "Explore, Choose, and Immerse Yourself in Movie Magic",
        slideind = 1,
        illustration = R.drawable.marvel_superheroes_onboarding,
        backgroundColor = MarvelColors.blue_500
    ),
    DataOnBoarding(
        label = "Easy Booking and Enjoyment",
        subLabel = "Seamless Booking, Instant Joy – Your Ultimate Movie Experience Awaits",
        slideind = 2,
        illustration = R.drawable.error_awkward_gif,
        backgroundColor = MarvelColors.red_500
    ),
    )


@Composable
fun TopContent(modifier: Modifier = Modifier, navigate_to_login: () -> Unit, pageInd: Int) {
    Row(
        modifier = modifier.padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Icon(
            modifier = Modifier.size(36.dp),
            painter = painterResource(R.drawable.ic_launcher_foreground),
            tint = Color.Black,
            contentDescription = "Logo"
        )

        TextButton(onClick = navigate_to_login, enabled = pageInd < 2) {
            Text(
                text = if (pageInd < 2) "skip" else "",
                color = Color.Black,
                style = MaterialTheme.typography.labelSmall
            )
        }

    }
}


@Composable
fun LabelGroup(page: DataOnBoarding, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.padding(horizontal = 10.dp)
    ) {

        Text(
            text = page.label,
            color = Color.Black,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
        )
        Text(
            text = page.subLabel,
            color = Color.Black,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
        )
    }
}


@Composable
fun WormPageIndicator(
    totalPages: Int = 1,
    currentPage: Int = 0,
    modifier: Modifier = Modifier,
    indicatorSize: Dp = 8.dp,
    color: Color = Color.White,
    spacing: Dp = indicatorSize,
    selectedMultiplier: Int = 3,
    vibrateOnSwipe: Boolean = true
) {

    assert(value = currentPage in 0 until totalPages,
        lazyMessage = { "Current page index is out of range." })

    val vibrator = LocalContext.current.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val coroutineScope = rememberCoroutineScope()

    val rowWidth =
        (indicatorSize * (selectedMultiplier + (totalPages - 1))) + (spacing * (totalPages - 1))
    Row(
        modifier = modifier.requiredWidth(rowWidth).animateContentSize(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 0 until totalPages) {
            val selected = i == currentPage

            val animSpec = remember {
                tween<Dp>(
                    durationMillis = DURATION_MILLIS,
                    easing = LinearEasing,
                    delayMillis = 0
                )
            }

            val height = indicatorSize
            val width: Dp by animateDpAsState(
                if (selected) indicatorSize * selectedMultiplier else indicatorSize,
                animationSpec = animSpec
            )

            Canvas(modifier = Modifier.size(width, height), onDraw = {
                drawRoundRect(
                    color = color,
                    cornerRadius = CornerRadius(height.toPx() / 2),
                    size = Size(width.toPx(), height.toPx())
                )
            })
        }
    }

    if (vibrateOnSwipe) {
        LaunchedEffect(currentPage) {
            coroutineScope.launch {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        60,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            }
        }
    }
}

private const val INDICATOR_FADE_IN_ANIMATION_DELAY = 200
private const val DURATION_MILLIS = 150

@Preview
@Composable
fun PreviewWormPageIndicator(){
        WormPageIndicator(
            totalPages = 3,
            currentPage = 1
        )
}


@Composable
fun OnBoardingScreenCicle(
    slides: List<DataOnBoarding> = onBoardingSlides, navigate_to_login: () -> Unit = {}
) {
    //TODO add  text trasition and aninmation

    /*  // Not Using for now :
        //screen orientation check
        var orientation by remember { mutableStateOf(Configuration.ORIENTATION_PORTRAIT) }
        val configuration = LocalConfiguration.current*/

    CircleRevealPager(slides = slides, navigate_to_login = navigate_to_login)
}

@Preview
@Composable
fun PreviewOnBoardingScreen() {
    OnBoardingScreenCicle()
}
