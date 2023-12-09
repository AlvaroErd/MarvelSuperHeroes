package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.android.material.math.MathUtils.lerp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarousel(
    images: List<String>,
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        images.size
    },
    autoScrollDuration: Long = 5_000,
) {
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    if (!isDragged) {
        with(pagerState) {
            var currentPageKey by remember { mutableIntStateOf(0) }
            LaunchedEffect(key1 = currentPageKey) {
                launch {
                    delay(timeMillis = autoScrollDuration)
                    val nextPage = (currentPage + 1).mod(images.size)
                    animateScrollToPage(page = nextPage)
                    currentPageKey = nextPage
                }
            }
        }
    }

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp,
            userScrollEnabled = true,
            contentPadding = PaddingValues(horizontal = 32.dp),
        ) { index ->
            AsyncImage(
                model = images[index],
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .carouselTransition(index, pagerState),
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit,
            )
        }

        DotIndicators(
            pageCount = images.size,
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun Modifier.carouselTransition(page: Int, pagerState: PagerState) =
    graphicsLayer {
        val pageOffset =
            ((pagerState.currentPage - page).toFloat() + pagerState.currentPageOffsetFraction).absoluteValue
        val transformation = lerp(
            0.7f,
            1.0f,
            1f - pageOffset.coerceIn(0f, 1f)
        )
        alpha = transformation
        scaleY = transformation
    }

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DotIndicators(
    pageCount: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    selectedColor: Color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
    unselectedColor: Color = androidx.compose.material3.MaterialTheme.colorScheme.secondary,
    dotSize: Dp = 6.dp,
    dotSpacing: Dp = 3.dp,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dotSpacing)
    ) {
        repeat(pageCount) { index ->
            DotIndicator(
                selected = index == pagerState.currentPage,
                selectedColor = selectedColor,
                unselectedColor = unselectedColor,
                dotSize = dotSize
            )
        }
    }
}

@Composable
private fun DotIndicator(
    selected: Boolean,
    selectedColor: Color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
    unselectedColor: Color = androidx.compose.material3.MaterialTheme.colorScheme.secondary,
    dotSize: Dp = 6.dp,
) {
    val color = if (selected) {
        selectedColor
    } else {
        unselectedColor
    }

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(color)
            .size(dotSize)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun DotIndicatorsPreview() {

    DotIndicators(
        pageCount = 4,
        pagerState = rememberPagerState(
            initialPage = 2,
            initialPageOffsetFraction = 0f
        ) {
            4
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true)
fun ImageCarouselPreview() {
    val images = listOf(
        "https://picsum.photos/id/237/200/300",
        "https://picsum.photos/id/237/200/300",
        "https://picsum.photos/id/237/200/300"
    )
    ImageCarousel(images = images)
}
