package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun AnimatedBorderCard(
    modifier: Modifier,
    contents: @Composable RowScope.() -> Unit
) {
    val containerSize = 200.dp
    var offsetFloat by remember { mutableStateOf(0f) }
    LaunchedEffect(null) {
        delay(200)
        offsetFloat = containerSize.value * 10f
    }
    val offset by animateFloatAsState(
        targetValue = offsetFloat,
        animationSpec = repeatable(
            iterations = 1,
            animation = tween(durationMillis = 1000, easing = LinearEasing),
        ),
        label = "",
    )

    val brush = Brush.linearGradient(
        shineColors,
        start = Offset(offset, offset),
        end = Offset(offset + containerSize.value * 40, offset + containerSize.value * 40),
        tileMode = TileMode.Repeated
    )

    Box(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp))
                .border(
                    width = 5.dp, brush = brush, shape = RoundedCornerShape(24.dp)
                )
                .background(Color(0xFFA9A9A9))
                .blur(2.dp),
        ) {
            contents()
        }
    }
}

private val shineColors = listOf(
    Color.White,
    Color(0xFFE91E63),
)

@ReferenceDevices
@Composable
fun AnimatedBorderPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedBorderCard(
            modifier = Modifier.size(300.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Metehan")
                Text(text = "Metehan")
                Text(text = "Metehan")
            }
        }
    }
}

@Preview(name = "phone", device = Devices.PHONE, showBackground = true)
@Preview(name = "foldable", device = Devices.FOLDABLE, showBackground = true)
@Preview(name = "desktop", device = Devices.DESKTOP, showBackground = true)
annotation class ReferenceDevices
