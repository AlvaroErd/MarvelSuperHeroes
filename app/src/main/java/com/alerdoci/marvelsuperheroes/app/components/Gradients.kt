package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun LinearGradient() {
    val gradient = Brush.linearGradient(
        0.0f to Color.Magenta,
        500.0f to Color.Cyan,
        start = Offset.Zero,
        end = Offset.Infinite
    )
    Box(modifier = Modifier.background(gradient).fillMaxSize())
}
@Preview(showBackground = true)
@Composable
fun HorizontalGradient() {
    val gradient = Brush.horizontalGradient(
        0.0f to Color.Magenta,
        1.0f to Color.Cyan,
        startX = 0.0f,
        endX = 1000.0f
    )
    Box(modifier = Modifier.background(gradient).fillMaxSize())
}
@Preview(showBackground = true)
@Composable
fun VerticalGradient() {
    val gradient = Brush.verticalGradient(
        0.0f to Color.Magenta,
        1.0f to Color.Cyan,
        startY = 0.0f,
        endY = 1500.0f
    )
    Box(modifier = Modifier.background(gradient).fillMaxSize())
}
@Preview(showBackground = true)
@Composable
fun RadialGradient() {
    val gradient = Brush.radialGradient(
        0.0f to Color.Magenta,
        1.0f to Color.Cyan,
        radius = 1500.0f,
        tileMode = TileMode.Repeated
    )
    Box(modifier = Modifier.background(gradient).fillMaxSize())
}