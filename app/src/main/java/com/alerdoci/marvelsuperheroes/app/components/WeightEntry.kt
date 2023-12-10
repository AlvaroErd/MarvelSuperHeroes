package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

val ScaleGray = Color(239, 239, 239)
val ScaleAccent = Color(119, 49, 255)

@Composable
fun WeightScale(
    weight: Int,
    valueRange: ClosedRange<Int>,
    modifier: Modifier
) {
    val animatedValue = remember { Animatable(weight, Int.VectorConverter) }
    LaunchedEffect(weight) {
        animatedValue.animateTo(weight)
    }

    Box(
        modifier = modifier
            .padding(16.dp)
            .weightScaleBackground(animatedValue.value, valueRange),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = buildAnnotatedString {
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                append("$weight")
                pop()
                append(" kg")
            },
            fontSize = 40.sp
        )
    }
}

private fun Modifier.weightScaleBackground(
    weight: Int,
    valueRange: ClosedRange<Int>,
) = drawBehind {
    val radius = size.width / 2
    val squareSize = Size(radius * 2, radius * 2)
    drawArc(
        color = ScaleGray,
        startAngle = 165f,
        sweepAngle = 210f,
        useCenter = false,
        size = squareSize,
        style = Stroke(width = 12f, cap = StrokeCap.Round)
    )

    drawArc(
        color = ScaleAccent,
        startAngle = -105f,
        sweepAngle = 30f,
        useCenter = true,
        topLeft = Offset(
            squareSize.width / 2 - squareSize.div(8f).width,
            (squareSize.height - squareSize.height / 1.1f) / 2
        ),
        size = squareSize.div(4f),
        style = Fill
    )

    drawArc(
        color = ScaleGray,
        startAngle = 165f,
        sweepAngle = 210f,
        useCenter = false,
        topLeft = Offset(
            (squareSize.width - squareSize.width / 1.1f) / 2,
            (squareSize.height - squareSize.height / 1.1f) / 2
        ),
        size = squareSize.div(1.1f),
        style = Stroke(width = 20f, cap = StrokeCap.Round)
    )

    ((0..14) - 7).forEach { index ->
        val alpha = (165f + index * 15f) / 180f * PI.toFloat()
        drawLine(
            color = ScaleGray,
            start = Offset(
                x = radius + (radius / 1.12f) * cos(alpha),
                y = radius + (radius / 1.12f) * sin(alpha),
            ),
            end = Offset(
                x = radius + (radius / 1.3f) * cos(alpha),
                y = radius + (radius / 1.3f) * sin(alpha),
            ),
            strokeWidth = 20f,
            cap = StrokeCap.Round
        )
    }

    drawArc(
        color = ScaleAccent,
        startAngle = 165f,
        sweepAngle = (210f / (valueRange.endInclusive - valueRange.start)) * (weight - valueRange.start),
        useCenter = false,
        topLeft = Offset(
            (squareSize.width - squareSize.width / 1.1f) / 2,
            (squareSize.height - squareSize.height / 1.1f) / 2
        ),
        size = squareSize.div(1.1f),
        style = Stroke(width = 30f, cap = StrokeCap.Round)
    )
}

@Composable
fun rememberWeightEntryState(
    initialValue: Int,
    valueRange: ClosedRange<Int>,
): WeightEntryState {
    val state = remember {
        WeightEntryState(
            initialValue = initialValue.toFloat(),
            valueRange = valueRange.start.toFloat()..valueRange.endInclusive.toFloat()
        )
    }

    LaunchedEffect(initialValue, valueRange) {
        state.updateBounds(valueRange.start.toFloat()..valueRange.endInclusive.toFloat())
        state.snapToValue(initialValue.toFloat())
    }
    return state
}

class WeightEntryState(
    initialValue: Float,
    var valueRange: ClosedFloatingPointRange<Float>
) {
    private val velocityTracker = VelocityTracker()
    private val animatedValue = Animatable(initialValue)

    val value: Float
        get() = animatedValue.value

    fun updateBounds(valueRange: ClosedFloatingPointRange<Float>) {
        this.valueRange = valueRange
        animatedValue.updateBounds(valueRange.start, valueRange.endInclusive)
    }

    suspend fun changeValueBy(number: Float) {
        animatedValue.snapTo((animatedValue.value - number))
    }

    suspend fun roundValue() {
        animatedValue.animateTo(
            animatedValue.value
                .roundToInt()
                .toFloat()
        )
    }

    suspend fun snapToValue(newValue: Float) {
        animatedValue.snapTo(newValue)
    }

    fun onDrag(durationMillis: Long, position: Float) {
        velocityTracker.addPosition(
            durationMillis,
            Offset(position, 0f)
        )
    }

    suspend fun onDragEnd(fraction: Float) {
        val decay = exponentialDecay<Float>(
            frictionMultiplier = fraction,
            absVelocityThreshold = 0.1f
        )
        val velocity = velocityTracker.calculateVelocity().x
        animatedValue.animateDecay(velocity, decay)
        roundValue()
    }
}

@Composable
fun WeightEntry(
    state: WeightEntryState,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        WeightNumbersRow(state)
        WeightIndicatorsRow(state)
        WeightSliderRow(
            state = state,
            onValueChange = { newValue ->
                coroutineScope.launch { state.snapToValue(newValue) }
            },
            onValueChangeFinished = {
                coroutineScope.launch { state.roundValue() }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun WeightNumbersRow(
    state: WeightEntryState,
    modifier: Modifier = Modifier
) {
    val currentValue = state.value
    val visibleRange = 5

    Box(modifier = modifier.decayingScroll(state, visibleRange, 0.6f)) {
        ((currentValue.toInt() - 3)..(currentValue.toInt() + 3))
            .filter { it.toFloat() in state.valueRange }
            .forEach { number ->
                val scale = ((2f - abs(number - currentValue).coerceAtMost(2f))
                    .pow(2f) + 2f) / 6f

                val color = lerp(
                    start = ScaleAccent,
                    stop = Color.LightGray,
                    fraction = abs(number - currentValue).coerceAtMost(1f)
                )

                Text(
                    text = "$number",
                    fontSize = 48.sp,
                    color = color,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(4.dp)
                        .offsetByValue(number, currentValue, visibleRange)
                        .scale(scale)
                )
            }
    }
}

@Composable
private fun WeightIndicatorsRow(
    state: WeightEntryState,
    modifier: Modifier = Modifier
) {
    val currentValue = state.value
    val visibleRange = 45

    Box(modifier = modifier.decayingScroll(state, visibleRange, 2.2f)) {
        ((currentValue.toInt() - 30)..(currentValue.toInt() + 30))
            .filter { it % 5 == 0 && it.toFloat() in state.valueRange }
            .forEach { number ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.offsetByValue(number, currentValue, visibleRange)
                ) {
                    val width = (30f - (abs(number - currentValue).coerceAtMost(30f))) / 10f
                    if (number % 10 == 0) {
                        Box(
                            modifier = Modifier
                                .width(width.dp)
                                .background(Color.Gray)
                                .height(24.dp)
                        )
                        Text(text = "$number")
                    } else {
                        Box(
                            modifier = Modifier
                                .width(width.dp)
                                .background(Color.Gray)
                                .height(12.dp)
                        )
                    }
                }
            }
    }
}

fun Modifier.decayingScroll(
    state: WeightEntryState,
    coefficient: Int,
    fraction: Float
): Modifier = composed {
    val coroutineScope = rememberCoroutineScope()
    pointerInput(Unit) {
        detectHorizontalDragGestures(
            onDragEnd = {
                coroutineScope.launch { state.onDragEnd(fraction) }
            }
        ) { change, dragAmount ->
            val scrolledValue = (dragAmount / size.width) * coefficient
            coroutineScope.launch { state.changeValueBy(scrolledValue) }
            state.onDrag(change.uptimeMillis, - (change.position.x / size.width) * coefficient)
            change.consumePositionChange()
        }
    }
}

fun Modifier.offsetByValue(
    number: Int,
    currentValue: Float,
    coefficient: Int
): Modifier {
    return layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        layout(constraints.maxWidth, placeable.height) {
            val itemWidthPx = constraints.maxWidth / coefficient
            val xCenter = constraints.maxWidth / 2 - placeable.width / 2

            val center = IntOffset(xCenter, 0)
            val placement = center + IntOffset(
                x = (itemWidthPx * (number - currentValue)).roundToInt(),
                y = 0
            )

            placeable.placeRelative(placement)
        }
    }
}

@Composable
private fun WeightSliderRow(
    state: WeightEntryState,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Slider(
        value = state.value,
        onValueChange = onValueChange,
        valueRange = state.valueRange,
        onValueChangeFinished = onValueChangeFinished,
        modifier = modifier
    )
}

@Preview
@Composable
fun WeightEntryPreview() {
    val state = rememberWeightEntryState(
        initialValue = 78,
        valueRange = 20..180
    )
    WeightEntry(
        state = state,
        modifier = Modifier.fillMaxWidth()
    )
}
