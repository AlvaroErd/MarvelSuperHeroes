package com.alerdoci.marvelsuperheroes.app.common.annotations

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.components.AdaptiveListItem
import com.alerdoci.marvelsuperheroes.app.components.UserProfile
import com.alerdoci.marvelsuperheroes.app.components.UserProfileComposable
import com.alerdoci.marvelsuperheroes.app.components.UserProfileProvider

@Preview(name = "Landscape Mode", showBackground = true, device = Devices.AUTOMOTIVE_1024p, widthDp = 640)
@Preview(name = "Portrait Mode", showBackground = true, device = Devices.PIXEL_7)
annotation class OrientationPreviews


@OrientationPreviews
@Composable
fun OrientationPreviews2Annotation() {
    MaterialTheme {
        Surface {
            AdaptiveListItem(
                imageResource = painterResource(id = R.drawable.ic_eye),
                text = "Write the `Preview` Medium article",
                isChecked = false,
                onCheckedChange = { }
            )
        }
    }
}

