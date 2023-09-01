package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


//We can customize the Brush direction
@Preview(showBackground = true)
@Composable
fun GradientText (modifier: Modifier = Modifier) {
    Text(
        fontSize = 30.sp,
        text = ("Get started with Android (Kotlin, Jet Compose) & IOS (Swift UI), " +
                "MVVM clean architecture, and Beautiful UI UX design patterns."),
        style = TextStyle(
            brush = Brush.linearGradient(
                colors = listOf(Color.Magenta, Color.Cyan)
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
fun MultipleColorsGradientText() {

    val gradientColors = listOf(Color.Cyan, Color.Blue, Color.White)

    Box(modifier = Modifier.background(Color.Black))
    {
        Text(
            fontSize = 30.sp,

            text = ("Get started with Android (Kotlin, Jet Compose) & IOS (Swift UI), " +
                    "MVVM clean architecture, and Beautiful UI UX design patterns."),
            style = TextStyle(
                brush = Brush.horizontalGradient(
                    colors = gradientColors
                )
            )
        )
    }
}

