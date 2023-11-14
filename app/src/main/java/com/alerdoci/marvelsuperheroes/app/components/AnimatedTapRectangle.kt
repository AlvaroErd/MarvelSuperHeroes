package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@Composable
fun AnimatedTapRectangle() {

    val coroutine = rememberCoroutineScope()

    val offsetX = remember { Animatable(10f) }
    val offsetY = remember { Animatable(10f) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    coroutine.launch {
                        offsetX.animateTo(offset.x)
                    }
                    coroutine.launch {
                        offsetY.animateTo(offset.y)
                    }
                }
            }
    ) {
        drawRect(
            color = Color.Blue,
            topLeft = Offset(offsetX.value, offsetY.value),
            size = Size(100f, 100f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedTapRectanglePreview() {
    AnimatedTapRectangle()
}
