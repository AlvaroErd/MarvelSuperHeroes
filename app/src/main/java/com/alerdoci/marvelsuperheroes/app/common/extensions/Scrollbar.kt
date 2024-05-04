/*
 * Copyright (c) 2022-2023 Balázs Püspök-Kiss (Illyan)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alerdoci.marvelsuperheroes.app.common.extensions

import android.annotation.SuppressLint
import android.view.ViewConfiguration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastSumBy
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest


//import timber.log.Timber

// https://gist.github.com/mxalbert1996/33a360fcab2105a31e5355af98216f5a

fun Modifier.drawHorizontalScrollbar(
    state: ScrollState,
    reverseScrolling: Boolean = false,
    topPadding: Dp = 0.dp,
    bottomPadding: Dp = 0.dp,
): Modifier = drawScrollbar(
    state,
    Orientation.Horizontal,
    reverseScrolling,
    topPadding = topPadding,
    bottomPadding = bottomPadding,
)

fun Modifier.drawVerticalScrollbar(
    state: ScrollState,
    reverseScrolling: Boolean = false,
    topPadding: Dp = 0.dp,
    bottomPadding: Dp = 0.dp,
): Modifier = drawScrollbar(
    state,
    Orientation.Vertical,
    reverseScrolling,
    topPadding = topPadding,
    bottomPadding = bottomPadding,
)

private fun Modifier.drawScrollbar(
    state: ScrollState,
    orientation: Orientation,
    reverseScrolling: Boolean,
    topPadding: Dp = 0.dp,
    bottomPadding: Dp = 0.dp,
): Modifier = drawScrollbar(
    orientation,
    reverseScrolling,
    topPadding = topPadding,
    bottomPadding = bottomPadding,
) { reverseDirection, atEnd, color, alpha, density ->
    if (state.maxValue > 0) {
        val canvasSize = if (orientation == Orientation.Horizontal) size.width else size.height
        val totalSize = canvasSize + state.maxValue - bottomPadding.value * density
        val thumbSize = canvasSize / totalSize * canvasSize
        val startOffset = topPadding.value * density + state.value / totalSize * canvasSize
        drawScrollbar(
            orientation,
            reverseDirection,
            atEnd,
            color,
            alpha,
            thumbSize,
            startOffset,
        )
    }
}

fun Modifier.drawHorizontalScrollbar(
    state: LazyListState,
    reverseScrolling: Boolean = false,
    topPadding: Dp = 0.dp,
    bottomPadding: Dp = 0.dp,
): Modifier = drawScrollbar(
    state,
    Orientation.Horizontal,
    reverseScrolling,
    topPadding = topPadding,
    bottomPadding = bottomPadding,
)

fun Modifier.drawVerticalScrollbar(
    state: LazyListState,
    reverseScrolling: Boolean = false,
    topPadding: Dp = 0.dp,
    bottomPadding: Dp = 0.dp,
): Modifier = drawScrollbar(
    state,
    Orientation.Vertical,
    reverseScrolling,
    topPadding = topPadding,
    bottomPadding = bottomPadding,
)

private fun Modifier.drawScrollbar(
    state: LazyListState,
    orientation: Orientation,
    reverseScrolling: Boolean,
    topPadding: Dp = 0.dp,
    bottomPadding: Dp = 0.dp,
): Modifier = drawScrollbar(
    orientation,
    reverseScrolling,
    topPadding = topPadding,
    bottomPadding = bottomPadding,
) { reverseDirection, atEnd, color, alpha, density ->
    val layoutInfo = state.layoutInfo
    val paddingSize = (bottomPadding.value + topPadding.value) * density
    val viewportSize = layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset - paddingSize
    val items = layoutInfo.visibleItemsInfo
    val itemsSize = items.fastSumBy { it.size }
    if (items.size < layoutInfo.totalItemsCount || itemsSize > viewportSize) {
        val estimatedItemSize = if (items.isEmpty()) 0f else itemsSize.toFloat() / items.size
        val totalSize = estimatedItemSize * layoutInfo.totalItemsCount
        val canvasSize = (if (orientation == Orientation.Horizontal) size.width else size.height) -
                (topPadding.value + bottomPadding.value) * density
        val thumbSize = viewportSize / totalSize * canvasSize
        val startOffset = if (items.isEmpty()) 0f else items.first().run {
            (estimatedItemSize * index - offset) / totalSize * canvasSize + bottomPadding.value * density
        }
        drawScrollbar(
            orientation,
            reverseDirection,
            atEnd,
            color,
            alpha,
            thumbSize,
            startOffset,
        )
    }
}

fun Modifier.drawVerticalScrollbar(
    state: LazyGridState,
    spanCount: Int,
    reverseScrolling: Boolean = false,
    topPadding: Dp = 0.dp,
    bottomPadding: Dp = 0.dp,
): Modifier = drawScrollbar(
    Orientation.Vertical,
    reverseScrolling,
    topPadding = topPadding,
    bottomPadding = bottomPadding,
) { reverseDirection, atEnd, color, alpha, _ ->
    val layoutInfo = state.layoutInfo
    val viewportSize = layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset
    val items = layoutInfo.visibleItemsInfo
    val rowCount = (items.size + spanCount - 1) / spanCount
    var itemsSize = 0
    for (i in 0 until rowCount) {
        itemsSize += items[i * spanCount].size.height
    }
    if (items.size < layoutInfo.totalItemsCount || itemsSize > viewportSize) {
        val estimatedItemSize = if (rowCount == 0) 0f else itemsSize.toFloat() / rowCount
        val totalRow = (layoutInfo.totalItemsCount + spanCount - 1) / spanCount
        val totalSize = estimatedItemSize * totalRow
        val canvasSize = size.height
        val thumbSize = viewportSize / totalSize * canvasSize
        val startOffset = if (rowCount == 0) 0f else items.first().run {
            val rowIndex = index / spanCount
            (estimatedItemSize * rowIndex - offset.y) / totalSize * canvasSize
        }
        drawScrollbar(
            Orientation.Vertical,
            reverseDirection,
            atEnd,
            color,
            alpha,
            thumbSize,
            startOffset,
        )
    }
}

private fun DrawScope.drawScrollbar(
    orientation: Orientation,
    reverseDirection: Boolean,
    atEnd: Boolean,
    color: Color,
    alpha: () -> Float,
    thumbSize: Float,
    startOffset: Float,
) {
    val thicknessPx = Thickness.toPx()
    val topLeft = when (orientation) {
        Orientation.Horizontal -> Offset(
            if (reverseDirection) size.width - startOffset - thumbSize else startOffset,
            if (atEnd) size.height - thicknessPx else 0f
        )
        Orientation.Vertical -> Offset(
            if (atEnd) size.width - thicknessPx else 0f,
            if (reverseDirection) size.height - startOffset - thumbSize else startOffset
        )
    }
    val size = when (orientation) {
        Orientation.Horizontal -> Size(thumbSize, thicknessPx)
        Orientation.Vertical -> Size(thicknessPx, thumbSize)
    }

    val radius = minOf(size.height, size.width) / 2
    drawRoundRect(
        color = color,
        topLeft = topLeft,
        size = size,
        alpha = alpha(),
        cornerRadius = CornerRadius(x = radius, y = radius)
    )
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
private fun Modifier.drawScrollbar(
    orientation: Orientation,
    reverseScrolling: Boolean,
    topPadding: Dp = 0.dp,
    bottomPadding: Dp = 0.dp,
    onDraw: DrawScope.(
        reverseDirection: Boolean,
        atEnd: Boolean,
        color: Color,
        alpha: () -> Float,
        density: Float
    ) -> Unit
): Modifier = composed {
    val scrolled = remember {
        MutableSharedFlow<Unit>(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    }
    val nestedScrollConnection = remember(orientation, scrolled) {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
//                Timber.v("Scroll position: x = ${consumed.x} y = ${consumed.y}")
                val delta = if (orientation == Orientation.Horizontal) consumed.x else consumed.y
                if (delta != 0f) scrolled.tryEmit(Unit)
                return Offset.Zero
            }
        }
    }

    val alpha = remember { Animatable(0f) }
    LaunchedEffect(scrolled, alpha) {
        scrolled.collectLatest {
            alpha.snapTo(1f)
            delay(ViewConfiguration.getScrollDefaultDelay().toLong())
            alpha.animateTo(0f, animationSpec = FadeOutAnimationSpec)
        }
    }

    val isLtr = LocalLayoutDirection.current == LayoutDirection.Ltr
    val reverseDirection = if (orientation == Orientation.Horizontal) {
        if (isLtr) reverseScrolling else !reverseScrolling
    } else reverseScrolling
    val atEnd = if (orientation == Orientation.Vertical) isLtr else true

    val color = BarColor
    val density = LocalDensity.current.density

    Modifier
        .nestedScroll(nestedScrollConnection)
        .drawWithContent {
            drawContent()
            onDraw(reverseDirection, atEnd, color, alpha::value, density)
        }
}

private val BarColor: Color
    @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)

private val Thickness = 4.dp
private val FadeOutAnimationSpec =
    tween<Float>(durationMillis = ViewConfiguration.getScrollBarFadeDuration())

// source: https://stackoverflow.com/a/68056586/16720445
fun Modifier.simpleVerticalScrollbar(
    state: LazyListState,
    width: Dp = 8.dp
): Modifier = composed {
    val targetAlpha = if (state.isScrollInProgress) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 500

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration)
    )
    val color = contentColorFor(MaterialTheme.colorScheme.onSurface)

    drawWithContent {
        drawContent()

        val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index
        val needDrawScrollbar = state.isScrollInProgress || alpha > 0.0f

        // Draw scrollbar if scrolling or if the animation is still running and lazy column has content
        if (needDrawScrollbar && firstVisibleElementIndex != null) {
            val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
            val scrollbarOffsetY = firstVisibleElementIndex * elementHeight
            val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight

            drawRect(
                color = color,
                topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                size = Size(width.toPx(), scrollbarHeight),
                alpha = alpha,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ScrollbarExampleScreen(
    modifier: Modifier = Modifier
) {
    val items = (0..50).toList()
    Column(
        modifier = modifier
    ) {
        val rowState = rememberLazyListState()
        LazyRow(
            modifier = Modifier
                .drawHorizontalScrollbar(rowState)
                .fillMaxSize()
                .weight(1f),
            state = rowState,
        ) {
            items(items) {
                Text(
                    modifier = Modifier
                        .vertical()
                        .rotate(90f)
                        .padding(16.dp),
                    text = "Item number $it"
                )
            }
        }
        val columnState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier
                .drawVerticalScrollbar(columnState)
                .fillMaxSize()
                .weight(1f),
            state = columnState
        ) {
            items(items) {
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = "Item number $it",
                )
            }
        }
    }
}

fun Modifier.vertical() =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.height, placeable.width) {
            placeable.place(
                x = -(placeable.width / 2 - placeable.height / 2),
                y = -(placeable.height / 2 - placeable.width / 2)
            )
        }
    }

@Preview(widthDp = 400, heightDp = 400, showBackground = true)
@Composable
internal fun ScrollbarPreview() {
    val state = rememberScrollState()
    Column(
        modifier = Modifier
            .drawVerticalScrollbar(state)
            .verticalScroll(state),
    ) {
        repeat(50) {
            Text(
                text = "Item ${it + 1}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}
@Preview(widthDp = 400, heightDp = 400, showBackground = true)
@Composable
fun LazyListScrollbarPreview() {
    val state = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.drawVerticalScrollbar(state),
        state = state
    ) {
        items(50) {
            Text(
                text = "Item ${it + 1}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@Preview(widthDp = 400, showBackground = true)
@Composable
fun HorizontalScrollbarPreview() {
    val state = rememberScrollState()
    Row(
        modifier = Modifier
            .drawHorizontalScrollbar(state)
            .horizontalScroll(state)
    ) {
        repeat(50) {
            Text(
                text = (it + 1).toString(),
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 16.dp)
            )
        }
    }
}

@Preview(widthDp = 400, showBackground = true)
@Composable
fun LazyListHorizontalScrollbarPreview() {
    val state = rememberLazyListState()
    LazyRow(
        modifier = Modifier.drawHorizontalScrollbar(state),
        state = state
    ) {
        items(50) {
            Text(
                text = (it + 1).toString(),
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 16.dp)
            )
        }
    }
}
