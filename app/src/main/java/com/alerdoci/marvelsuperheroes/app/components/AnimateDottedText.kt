package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun AnimateDottedText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    cycleDuration: Int = 1000 // Milliseconds
){
    val transition = rememberInfiniteTransition(label = "Dots Transition")

    // Define the animated value for the number of visible dots
    val visibleDotsCount = transition.animateValue(
        initialValue = 0,
        targetValue = 4,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = cycleDuration,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "Visible Dots Count"
    )

    // Display the text with dynamically changing dots based on the animation
    Text(
        text = text + ".".repeat(visibleDotsCount.value),
        modifier = modifier,
        style = style
    )
}

@Composable
fun LoadingDialog(
    message: String? = null
) {
    // Define the dialog with specific dismiss properties
    Dialog(
        onDismissRequest = { /* Never will happen, because of the specific properties */ },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        // Define the dialog surface
        Surface(
            shape = RoundedCornerShape(16.dp) // Specify any shape you like
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                // Space between the indicator and the text
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                // Add some space out of the edges
                modifier = Modifier.padding(16.dp)
            ) {
                // Display circular progress indicator
                CircularProgressIndicator()

                // Display the text if it's not null and not blank
                if (!message.isNullOrBlank()) {
                    AnimateDottedText(
                        text = message,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun AnimateDottedTextPreview() {
    LoadingDialog( message = "Cargando ")
}
