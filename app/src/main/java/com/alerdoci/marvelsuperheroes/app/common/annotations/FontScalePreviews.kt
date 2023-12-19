package com.alerdoci.marvelsuperheroes.app.common.annotations

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "0.5 Small font size", group = "font scales", fontScale = 0.5f)
@Preview(name = "1 Default Font Size", group = "font scales", fontScale = 1f)
@Preview(name = "1.5 Large font size", group = "font scales", fontScale = 1.5f)
@Preview(name = "2 Extra Large Font Size", group = "font scales", fontScale = 2f)
annotation class FontScalePreviews

@FontScalePreviews
@Composable
fun FontScalePreviewsAnnotation() {
    Text("Hello World")
}
