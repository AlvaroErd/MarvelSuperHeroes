package com.alerdoci.marvelsuperheroes.app.common.annotations

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.alerdoci.marvelsuperheroes.app.components.UserProfile
import com.alerdoci.marvelsuperheroes.app.components.UserProfileComposable
import com.alerdoci.marvelsuperheroes.app.components.UserProfileProvider

@Preview(name = "Left-To-Right", locale = "en")
@Preview(name = "Right-To-Left", locale = "ar")
annotation class LayoutDirectionPreviews

@LayoutDirectionPreviews
@Composable
fun LayoutDirectionPreviewsListItem(
    @PreviewParameter(UserProfileProvider::class, limit = 3) userProfile: UserProfile
) {
    MaterialTheme {
        Surface {
            UserProfileComposable(profile = userProfile)
        }
    }
}
