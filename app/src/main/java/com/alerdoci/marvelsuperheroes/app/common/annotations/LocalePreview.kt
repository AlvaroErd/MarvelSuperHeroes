package com.alerdoci.marvelsuperheroes.app.common.annotations

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "ESP", showBackground = true, locale = "es", device = Devices.PIXEL_7)
@Preview(name = "ENG", showBackground = true, locale = "en", device = Devices.PIXEL_7)
annotation class LocalePreview

@LocalePreview
@Composable
fun EngEspPreviewAnnotation() {
    Text("Hello World")
}
