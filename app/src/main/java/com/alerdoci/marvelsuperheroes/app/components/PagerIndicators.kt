package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.lerp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import kotlin.math.absoluteValue


// https://www.sinasamaki.com/five-pager-indicator-animations/

//Some more
//https://blog.canopas.com/jetpack-compose-how-to-implement-custom-pager-indicators-8b6a01d63964

val NUM_OF_PAGES = 4

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.offsetForPage(page: Int) = (currentPage - page) + currentPageOffsetFraction
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.indicatorOffsetForPage(page: Int) =  1f - offsetForPage(page).coerceIn(-1f, 1f).absoluteValue

// OFFSET ONLY FROM THE LEFT
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.startOffsetForPage(page: Int): Float {
    return offsetForPage(page).coerceAtLeast(0f)
}

// OFFSET ONLY FROM THE RIGHT
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.endOffsetForPage(page: Int): Float {
    return offsetForPage(page).coerceAtMost(0f)
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CircleIndicator(
    modifier: Modifier = Modifier,
    state: PagerState
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        for (i in 0 until NUM_OF_PAGES) {
            val offset = state.indicatorOffsetForPage(i)
            Box(
                modifier = Modifier.size(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier
                        .size(
                            lerp(6.dp, 32.dp, offset)
                        )
                        .border(
                            width = 3.dp,
                            color = Color.Black,
                            shape = CircleShape,
                        )
                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LineIndicator(
    modifier: Modifier = Modifier,
    state: PagerState
) {
    Row(
        modifier = modifier
            .width(32.dp * NUM_OF_PAGES)
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        for (i in 0 until NUM_OF_PAGES) {
            val offset = state.indicatorOffsetForPage(i)
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .weight(.5f + (offset * 3f))
                    .height(8.dp)
                    .background(
                        color = Color.Black,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {

            }        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GlowIndicator(
    modifier: Modifier = Modifier,
    state: PagerState
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        for (i in 0 until NUM_OF_PAGES) {
            val offset = state.indicatorOffsetForPage(i)
            Box(
                modifier = Modifier
                    .size(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier
                        .size(
                            lerp(14.dp, 32.dp, offset)
                        )
                        .blur(
                            radius = lerp(0.dp, 16.dp, offset),
                            edgeTreatment = BlurredEdgeTreatment.Unbounded,
                        )
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Cyan,
                                    Color.Magenta,
                                )
                            ),
                            shape = CircleShape
                        )
                )
                Box(
                    Modifier
                        .size(
                            lerp(14.dp, 22.dp, offset)
                        )
                        .background(
                            color = Color.Black,
                            shape = CircleShape,
                        )
                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SquareIndicator(
    modifier: Modifier = Modifier,
    state: PagerState
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        for (i in 0 until NUM_OF_PAGES) {
            Box(
                modifier = Modifier.size(32.dp),
                contentAlignment = Alignment.Center
            ) {
                val offset = state.indicatorOffsetForPage(i)
                Box(
                    Modifier
                        .rotate(135f * offset)
                        .size(
                            lerp(12.dp, 22.dp, offset)
                        )
                        .border(
                            width = 3.dp,
                            color = Color.Black,
                            shape = RectangleShape,
                        )
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RomanNumeralIndicator(
    modifier: Modifier = Modifier,
    state: PagerState
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {

        val selectedTextStyle = remember {
            TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF000000)
            )
        }

        val defaultTextStyle = remember {
            TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Thin,
                color = Color.Black.copy(alpha = .5f)
            )
        }

        for (i in 0 until NUM_OF_PAGES) {
            Box(
                modifier = Modifier
                    .size(height = 48.dp, width = 36.dp)
                    .align(Alignment.CenterVertically),
                contentAlignment = Alignment.CenterEnd
            ) {
                val offset = state.indicatorOffsetForPage(i)
                Text(
                    text = (i + 1).toString(),
                    modifier = Modifier.fillMaxWidth(),
                    style = lerp(defaultTextStyle, selectedTextStyle, offset),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

