package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ColorChangeState() {

    var isButtonEnabled by remember { mutableStateOf(true) }

    val animatedButtonColor = animateColorAsState(
        targetValue = if (isButtonEnabled) Color.Green else Color.Red,
        animationSpec = tween(1000, 0, LinearEasing), label = ""
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = animatedButtonColor.value
            ),
            onClick = {
                isButtonEnabled = !isButtonEnabled
            }
        ) {
            Text(text = "Button")
        }
    }
}

@Preview
@Composable
fun ColorChangeStatePreview() {
    ColorChangeState()
}
