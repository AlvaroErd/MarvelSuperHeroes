package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


fun Color.Companion.fromHex(colorString: String): Color {
    return Color(android.graphics.Color.parseColor(colorString))
}

@Preview
@Composable
fun AnimationBlur() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val blueX by infiniteTransition.animateFloat(
        initialValue = -50.dp.value,
        targetValue = 0.dp.value,
        animationSpec = infiniteRepeatable(
            animation = tween(5000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val blueY by infiniteTransition.animateFloat(
        initialValue = -50.dp.value,
        targetValue = 0.dp.value,
        animationSpec = infiniteRepeatable(
            animation = tween(5000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val redX by infiniteTransition.animateFloat(
        initialValue = (-150).dp.value,
        targetValue = 150.dp.value,
        animationSpec = infiniteRepeatable(
            animation = tween(5000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    val lightBlueX by infiniteTransition.animateFloat(
        initialValue = 150.dp.value,
        targetValue = 129.dp.value,
        animationSpec = infiniteRepeatable(
            animation = tween(5000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    val lightBlueY by infiniteTransition.animateFloat(
        initialValue = 130.dp.value,
        targetValue = -130.dp.value,
        animationSpec = infiniteRepeatable(
            animation = tween(5000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .width(240.dp)
                .height(338.dp)
                .blur(90.dp)
                .background(Color.fromHex("#f48fb1"))
                .offset(x = blueX.dp, blueY.dp)
                .background(color = Color.fromHex("#283593"), shape = CircleShape)
                .offset(x = redX.dp, y = 90.dp)
                .background(color = Color.fromHex("#f44336"), shape = RoundedCornerShape(4.dp))
                .offset(x = lightBlueX.dp, y = lightBlueY.dp)
                .background(color = Color.fromHex("#039be5"), shape = GenericShape { size, _ ->
                    moveTo(size.width / 2f, 0f)
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                })

        )
    }
}

@Preview
@Composable
fun AnimationBlurPreview() {
    AnimationBlur()
}
