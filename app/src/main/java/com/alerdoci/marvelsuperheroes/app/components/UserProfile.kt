package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.alerdoci.marvelsuperheroes.R

data class UserProfile(val name: String, val bio: String, val avatar: Int)

class UserProfileProvider : PreviewParameterProvider<UserProfile> {
    override val values = sequenceOf(
        UserProfile("Alice", "Loves hiking and coffee", R.drawable.groot_placeholder),
        UserProfile("Bob", "Avid reader and tech enthusiast", R.drawable.cheems),
        UserProfile("Charlie", "Just here for the memes", R.drawable.marvel_superheroes_onboarding)
    )
}

@Composable
fun UserProfileComposable(profile: UserProfile) {
    Card {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(
                painter = painterResource(profile.avatar),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(text = profile.name)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = profile.bio)
            }
        }

    }
}

@Composable
@Preview
fun UserProfilePreview(
    @PreviewParameter(UserProfileProvider::class, limit = 3) userProfile: UserProfile
) {
    MaterialTheme {
        Surface {
            UserProfileComposable(profile = userProfile)
        }
    }
}
