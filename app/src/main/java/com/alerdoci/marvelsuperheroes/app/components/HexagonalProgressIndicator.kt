package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun Hexagon(
    modifier: Modifier = Modifier,
    isFilled: Boolean,
    icon: ImageVector? = null,
    hexagonColor: Color,
    backgroundColor: Color,
    iconTint: Color = Color.White,
    onClick: (() -> Unit)? = null,
    shouldAnimateLoadingBar: Boolean = false
) {
    var clickAnimationOffset by remember { mutableStateOf(Offset.Zero) }
    var canvasSize by remember { mutableStateOf(Size.Zero) }
    var animationRadius by remember { mutableStateOf(0f) }
    var animationRotation by remember { mutableStateOf(0f) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        if (shouldAnimateLoadingBar) {
            animate(
                0f,
                360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        1000,
                        delayMillis = 0,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            ) { value, _ ->
                animationRotation = value
            }
        }
    }



    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {
                    detectTapGestures(
                        onTap = { offset ->
                            if (onClick == null) {
                                return@detectTapGestures
                            }
                            onClick()
                            clickAnimationOffset = offset
                            coroutineScope.launch {
                                animate(
                                    0f,
                                    canvasSize.height * 2,
                                    animationSpec = tween(200)
                                ) { value, _ ->
                                    animationRadius = value
                                }
                                animationRadius = 0f
                            }
                        }
                    )
                },
        ) {
            val height = size.height
            val width = size.width
            canvasSize = Size(width, height)

            val path = Path().apply {
                moveTo(width / 2f, 0f)
                lineTo(width, height / 4)
                lineTo(width, height / 4 * 3)
                lineTo(width / 2, height)
                lineTo(0f, height / 4 * 3)
                lineTo(0f, height / 4)
                close()
            }

            if (shouldAnimateLoadingBar) {
                clipPath(path) {
                    rotate(animationRotation) {
                        drawArc(
                            startAngle = 0f,
                            sweepAngle = 150f,
                            brush = Brush.sweepGradient(
                                colors = listOf(
                                    backgroundColor,
                                    backgroundColor,
                                    hexagonColor.copy(alpha = 0.5f),
                                    hexagonColor.copy(alpha = 0.5f),
                                    hexagonColor,
                                    hexagonColor,
                                    hexagonColor
                                )
                            ),
                            useCenter = true,
                            size = canvasSize * 1.1f
                        )
                    }
                }
            } else {
                drawPath(
                    path = path,
                    color = hexagonColor,
                    style = if (isFilled) Fill else Stroke(width = 1.dp.toPx())
                )
            }
            clipPath(path) {
                drawCircle(
                    Color.White.copy(alpha = 0.2f),
                    radius = animationRadius + 0.1f,
                    center = clickAnimationOffset
                )
            }
        }
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = "Hexagon Icon",
                modifier = Modifier.fillMaxSize(0.5f),
                tint = iconTint
            )
        }
    }
}

@Composable
fun HexagonSection(
    modifier: Modifier = Modifier,
    isScanning: Boolean,
    onScanButtonClick: () -> Unit,
    color: Color,
    backgroundColor: Color
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if (isScanning) {
            Hexagon(
                isFilled = false,
                hexagonColor = color,
                backgroundColor = backgroundColor,
                modifier = Modifier.fillMaxSize(),
                shouldAnimateLoadingBar = true
            )
        } else {
            Hexagon(
                isFilled = false,
                hexagonColor = color,
                backgroundColor = backgroundColor,
                modifier = Modifier.fillMaxSize()
            )
        }
        Hexagon(
            isFilled = true,
            hexagonColor = color,
            backgroundColor = backgroundColor,
            icon = Icons.Default.Search,
            modifier = Modifier.fillMaxSize(0.58f),
            onClick = { onScanButtonClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HexagonPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .aspectRatio(6 / 7f)
            .padding(15.dp)
    ) {
        Hexagon(
            isFilled = true,
            iconTint=Color.Blue,
            hexagonColor = Color.Blue,
            backgroundColor = Color.White,
            modifier = Modifier.fillMaxSize(),
            icon = Icons.Default.Refresh,
            onClick = {

            },
            shouldAnimateLoadingBar = true
        )
    }
}
