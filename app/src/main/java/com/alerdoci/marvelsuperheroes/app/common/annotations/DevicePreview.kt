package com.alerdoci.marvelsuperheroes.app.common.annotations

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(showSystemUi = true, name = "Nexus 10", device = Devices.NEXUS_10)
@Preview(showSystemUi = true, name = "Nexus 5", device = Devices.NEXUS_5)
@Preview(showSystemUi = true, name = "Nexus 7", device = Devices.NEXUS_7_2013)
@Preview(showSystemUi = true, name = "Foldable", device = Devices.FOLDABLE)
@Preview(showSystemUi = true, name = "Phone 600x800", device = Devices.PHONE, heightDp = 600, widthDp = 800)
@Preview(showSystemUi = true, name = "Phone 480x800", device = Devices.PHONE, heightDp = 480, widthDp = 800)
@Preview(showSystemUi = true, name = "Pixel 2", device = Devices.PIXEL_2)
@Preview(showSystemUi = true, name = "Pixel XL", device = Devices.PIXEL_XL)
@Preview(showSystemUi = true, name = "Pixel C", device = Devices.PIXEL_C)
annotation class DevicePreview

@DevicePreview
@Composable
fun FullPreviewAnnotation() {
    Text("Hello World")
}
