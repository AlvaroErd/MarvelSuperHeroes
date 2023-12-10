package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ClickableEffect() {
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(Color.Yellow)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false, color = Color.Magenta, radius = 200.dp)
            ) {

            }
    )
}

@Composable
@Preview(showBackground = true)
fun ClickableEffectPreview() {
    ClickableEffect()
}
