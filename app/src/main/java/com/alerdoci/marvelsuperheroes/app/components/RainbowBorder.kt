package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

fun Modifier.rainbowBorder(
    strokeWidth: Float,
    backgroundColor: Color = Color.White
): Modifier =
    drawWithContent {
        drawRect(color = backgroundColor, size = size)
        drawContent()
        drawRect(
            brush = Brush.linearGradient(
                listOf(Color.Magenta, Color.Cyan)
            ),
            size = size,
            style = Stroke(width = strokeWidth)
        )
    }

@Composable
fun RainbowBorderContent() {
    Text(
        text = "Metehan",
        modifier = Modifier
            .rainbowBorder(5f, Color.Gray)
            .padding(20.dp)
    )
}

@Preview
@Composable
fun RainbowBorderContentPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RainbowBorderContent()
    }
}
