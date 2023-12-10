package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
private fun PulsatingHeartIcon(infiniteTransition: InfiniteTransition) {
//1
    val pulsate by infiniteTransition.animateFloat(
        initialValue = 10f,
        targetValue = 60f,
        animationSpec = infiniteRepeatable(tween(1200), RepeatMode.Reverse), label = ""
    )
//2
    Icon(
        imageVector = Icons.Default.Favorite,
        contentDescription = "",
        modifier = Modifier
            .size(pulsate.dp)
            .offset(
                x = 10.dp, y = 10.dp
            )
    )

}

@Preview
@Composable
fun PulsatingHeartIconPreview() {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Gray)
            .padding(16.dp)
    ) {
        PulsatingHeartIcon(infiniteTransition)
    }
}

