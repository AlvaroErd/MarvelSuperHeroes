package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import kotlinx.coroutines.delay

@Composable
fun DNAAnimation(
    circleSize: Dp = 25.dp,
    firstColor: Color = Color(0xFFFF5722),
    secondColor: Color = Color(0xFF9C27B0),
    spaceBetween: Dp = 10.dp,
    travelDistance: Dp = 20.dp
){
    val delay = 200L
    val animationDuration = 2000
    val firstBallRevolution = getAnimationValue()
    firstBallRevolution.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * delay)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = animationDuration
                        // For smooth revolution(without jerk at end), adding multiple frames
                        0.0f at 0
                        -0.8f at 250
                        -1.0f at 500
                        -0.8f at 750
                        0.0f at 1000
                        0.8f at 1250
                        1.0f at 1500
                        0.8f at 1750
                        0.0f at 2000
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val secondBallRevolution = getAnimationValue()
    secondBallRevolution.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * delay)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = animationDuration
                        // For smooth revolution(without jerk at end), adding multiple frames
                        0.0f at 0
                        0.8f at 250
                        1.0f at 500
                        0.8f at 750
                        0.0f at 1000
                        -0.8f at 1250
                        -1.0f at 1500
                        -0.8f at 1750
                        0.0f at 2000
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val firstBallSize = getAnimationValue()
    firstBallSize.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * delay)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = animationDuration
                        0.0f at 0
                        1.0f at 900
                        1.0f at 1100
                        0.0f at 2000
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val secondBallSize = getAnimationValue()
    secondBallSize.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * delay)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = animationDuration
                        1.0f at 0
                        1.0f at 100
                        0.0f at 1000
                        1.0f at 1900
                        1.0f at 2000
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }
    val firstBallRevolValues = firstBallRevolution.map { it.value }
    val firstBallSizeValues = firstBallSize.map { it.value }
    val secondBallRevolValues = secondBallRevolution.map { it.value }
    val secondBallSizeValues = secondBallSize.map { it.value }
    val distance = with(LocalDensity.current) { travelDistance.toPx() }

    ConstraintLayout {
        Column(
            verticalArrangement = Arrangement.spacedBy(spaceBetween)) {
            secondBallRevolValues.forEachIndexed { index, value ->
                Box(modifier = Modifier.size(circleSize)) {
                    Box(
                        modifier = Modifier
                            .size(circleSize * secondBallSizeValues[index])
                            .graphicsLayer {
                                translationX = value * distance
                            }
                            .background(
                                color = firstColor,
                                shape = CircleShape
                            )
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(spaceBetween)) {
            firstBallRevolValues.forEachIndexed { index, value ->
                Box(modifier = Modifier.size(circleSize)) {
                    Box(
                        modifier = Modifier
                            .size(circleSize * firstBallSizeValues[index])
                            .graphicsLayer {
                                translationX = value * distance
                            }
                            .background(
                                color = secondColor,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun getAnimationValue() = listOf(
    remember { Animatable(initialValue = 0f) },
    remember { Animatable(initialValue = 0f) },
    remember { Animatable(initialValue = 0f) },
    remember { Animatable(initialValue = 0f) },
    remember { Animatable(initialValue = 0f) },
    remember { Animatable(initialValue = 0f) },
    remember { Animatable(initialValue = 0f) },
    remember { Animatable(initialValue = 0f) },
    remember { Animatable(initialValue = 0f) },
    remember { Animatable(initialValue = 0f) }
)
