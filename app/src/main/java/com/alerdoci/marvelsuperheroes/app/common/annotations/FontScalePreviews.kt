package com.alerdoci.marvelsuperheroes.app.common.annotations

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "small font",
    group = "font scales",
    fontScale = 0.5f
)
@Preview(
    name = "large font",
    group = "font scales",
    fontScale = 1.5f
)
annotation class FontScalePreviews

@Preview(
    name = "Spanish",
    group = "locale",
    locale = "es"
)
@FontScalePreviews
@Composable
fun HelloWorldPreview() {
    Text("Hello World")
}