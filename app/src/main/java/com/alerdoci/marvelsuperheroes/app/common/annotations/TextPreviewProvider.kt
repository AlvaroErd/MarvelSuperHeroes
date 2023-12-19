package com.alerdoci.marvelsuperheroes.app.common.annotations

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class TextPreviewProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf(
        "Short Text",
        "A bit longer text.",
        "This one is really, really long. Like, really long!"
    )
}

@Composable
@Preview
fun DifferentTextPreviewsListItem(
    @PreviewParameter(TextPreviewProvider::class) text: String
) {
    MaterialTheme {
        Surface {
            Text(text = text)
        }
    }
}
