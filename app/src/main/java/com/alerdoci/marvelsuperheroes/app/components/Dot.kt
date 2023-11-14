package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.ui.geometry.Offset
import kotlin.math.sqrt
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.isActive
import kotlin.math.pow
import kotlin.math.sqrt
import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import com.alerdoci.marvelsuperheroes.app.components.Dot.Companion.distanceTo
import com.alerdoci.marvelsuperheroes.app.components.Dot.Companion.next
import com.alerdoci.marvelsuperheroes.app.components.DotsAndLinesState.Companion.next
import com.alerdoci.marvelsuperheroes.app.components.DotsAndLinesState.Companion.populationControl
import com.alerdoci.marvelsuperheroes.app.components.DotsAndLinesState.Companion.sizeChanged
import kotlin.math.roundToInt

data class DotsAndLinesState(
    val dots: List<Dot> = emptyList(),
    val pointer: Dot? = null,
    val dotRadius: Float,
    val size: IntSize = IntSize.Zero,
    val speed: Float
) {

    companion object {
        // TODO(halilozercan): A real size changed algorithm instead of resetting everything
        fun DotsAndLinesState.sizeChanged(
            size: IntSize,
            populationFactor: Float
        ) : DotsAndLinesState {
            if (size == this.size) return this
            return copy(
                dots = (0..size.realPopulation(populationFactor)).map {
                    Dot.create(size)
                },
                size = size
            )
        }

        fun DotsAndLinesState.next(durationMillis: Long): DotsAndLinesState {
            return copy(
                dots = dots.map {
                    it.next(size, durationMillis, dotRadius, speed)
                }
            )
        }

        fun DotsAndLinesState.populationControl(populationFactor: Float): DotsAndLinesState {
            val count = size.realPopulation(populationFactor = populationFactor)

            return if(count < dots.size) {
                copy(dots = dots.shuffled().take(count))
            } else {
                copy(dots = dots + (0..count-dots.size).map { Dot.create(size) })
            }
        }

        private fun IntSize.realPopulation(populationFactor: Float): Int {
            return (width * height / 10_000 * populationFactor).roundToInt()
        }
    }
}
fun Modifier.dotsAndLines(
    contentColor: Color = Color.White,
    threshold: Float,
    maxThickness: Float,
    dotRadius: Float,
    speed: Float,
    populationFactor: Float
) = composed {
    val dotsAndLinesModel = remember {
        DotsAndLinesModel(
            DotsAndLinesState(
                dotRadius = dotRadius,
                speed = speed
            )
        )
    }

    LaunchedEffect(speed, dotRadius, populationFactor) {
        dotsAndLinesModel.populationControl(speed, dotRadius, populationFactor)
    }

    LaunchedEffect(Unit) {
        var lastFrame = 0L
        while (isActive) {
            val nextFrame = awaitFrame() / 100_000L
            if (lastFrame != 0L) {
                val period = nextFrame - lastFrame
                dotsAndLinesModel.next(period)
            }
            lastFrame = nextFrame
        }
    }

    pointerInput(Unit) {
        detectDragGestures(
            onDragStart = { offset ->
                dotsAndLinesModel.pointerDown(offset)
            },
            onDragEnd = {
                dotsAndLinesModel.pointerUp()
            },
            onDragCancel = {
                dotsAndLinesModel.pointerUp()
            },
            onDrag = { change, dragAmount ->
                dotsAndLinesModel.pointerMove(dragAmount)
                change.consumePositionChange()
            }
        )
    }
        .onSizeChanged {
            dotsAndLinesModel.sizeChanged(it, populationFactor)
        }
        .drawBehind {
            val allDots = with(dotsAndLinesModel.dotsAndLinesState) { (dots + pointer).filterNotNull() }

            allDots.forEach {
                drawCircle(contentColor, radius = dotRadius, center = it.position)
            }

            val realThreshold = threshold * sqrt(size.width.pow(2) + size.height.pow(2))

            allDots.nestedForEach { first, second ->
                val distance = first distanceTo second

                if (distance <= realThreshold) {
                    drawLine(
                        contentColor,
                        first.position,
                        second.position,
                        0.5f + (realThreshold - distance) * maxThickness / realThreshold
                    )
                }
            }
        }
}

@Immutable
class DotsAndLinesModel(
    initialDotsAndLinesState: DotsAndLinesState
) {
    var dotsAndLinesState by mutableStateOf(initialDotsAndLinesState)

    fun populationControl(
        speed: Float,
        dotRadius: Float,
        populationFactor: Float
    ) {
        dotsAndLinesState = dotsAndLinesState.copy(
            speed = speed,
            dotRadius = dotRadius
        ).populationControl(populationFactor)
    }

    fun next(period: Long) {
        dotsAndLinesState = dotsAndLinesState.next(period)
    }

    fun sizeChanged(size: IntSize, populationFactor: Float) {
        dotsAndLinesState = dotsAndLinesState.sizeChanged(
            size = size,
            populationFactor = populationFactor
        )
    }

    fun pointerDown(offset: Offset) {
        dotsAndLinesState = dotsAndLinesState.copy(
            pointer = Dot(
                position = offset,
                vector = Offset.Zero
            )
        )
    }

    fun pointerMove(offset: Offset) {
        val currentPointer = dotsAndLinesState.pointer ?: return

        dotsAndLinesState = dotsAndLinesState.copy(
            pointer = dotsAndLinesState.pointer?.copy(
                position = currentPointer.position + offset,
                vector = Offset.Zero
            )
        )
    }

    fun pointerUp() {
        dotsAndLinesState = dotsAndLinesState.copy(pointer = null)
    }
}

private fun <T> List<T>.nestedForEach(block: (T, T) -> Unit) {
    for (i in this.indices) {
        for (j in i + 1 until this.size) {
            block(this[i], this[j])
        }
    }
}
data class Dot(
    val position: Offset,
    val vector: Offset
) {
    companion object {
        /**
         * Calculate this [Dot]'s distance to another one.
         */
        infix fun Dot.distanceTo(another: Dot): Float {
            return (position - another.position).getDistance()
        }

        /**
         * Calculate where this dot will be in the next iteration.
         *
         * @param borders Size of the canvas where dots bounce.
         * @param durationMillis How long time is going to pass until next iteration.
         * @param dotRadius The radius of this dot when it is drawn.
         * @param speedCoefficient Although there is vector that indicates motion, this
         * parameter is used to speed up or down the animation at will.
         */
        fun Dot.next(
            borders: IntSize,
            durationMillis: Long,
            dotRadius: Float,
            speedCoefficient: Float
        ): Dot {
            val speed = vector * speedCoefficient

            return Dot(
                position = position + Offset(
                    x = speed.x / 1000f * durationMillis,
                    y = speed.y / 1000f * durationMillis,
                ),
                vector = vector
            ).let { (position, vector) ->
                val borderTop = dotRadius
                val borderLeft = dotRadius
                val borderBottom = borders.height - dotRadius
                val borderRight = borders.width - dotRadius
                Dot(
                    position = Offset(
                        x = when {
                            position.x < borderLeft -> borderLeft - (position.x - borderLeft)
                            position.x > borderRight -> borderRight - (position.x - borderRight)
                            else -> position.x
                        },
                        y = when {
                            position.y < borderTop -> borderTop - (position.y - borderTop)
                            position.y > borderBottom -> borderBottom - (position.y - borderBottom)
                            else -> position.y
                        }
                    ),
                    vector = Offset(
                        x = when {
                            position.x < borderLeft -> -vector.x
                            position.x > borderRight -> -vector.x
                            else -> vector.x
                        },
                        y = when {
                            position.y < borderTop -> -vector.y
                            position.y > borderBottom -> -vector.y
                            else -> vector.y
                        }
                    )
                )
            }
        }

        /**
         * Create a random dot instance belonging to @param borders.
         */
        fun create(borders: IntSize): Dot {
            return Dot(
                position = Offset(
                    (0..borders.width).random().toFloat(),
                    (0..borders.height).random().toFloat()
                ),
                vector = Offset(
                    // First, randomize direction. Second, randomize amplitude of speed vector.
                    listOf(-1f, 1f).random() * ((borders.width.toFloat() / 100f).toInt()..(borders.width.toFloat() / 10f).toInt()).random()
                        .toFloat(),
                    listOf(-1f, 1f).random() * ((borders.height.toFloat() / 100f).toInt()..(borders.height.toFloat() / 10f).toInt()).random()
                        .toFloat()
                )
            )
        }

        // Treat offset as a vector
        fun Offset.normalize(): Offset {
            val l = 1.0f / length()
            return Offset(x * l, y * l)
        }

        fun Offset.length(): Float {
            return sqrt(x * x + y * y)
        }
    }
}
