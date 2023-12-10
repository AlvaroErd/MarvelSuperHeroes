package com.alerdoci.marvelsuperheroes.app.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TextTransition(textAndColor: Pair<String, Color>) {
    AnimatedContent(
        targetState = textAndColor,
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        }, label = ""
    ) { (text, color) ->
        Row {
            text.forEachIndexed { index, char ->
                val stiffness = 300f - 200f * (index.toFloat() / (text.length - 1))
                Text(
                    modifier = Modifier.animateEnterExit(
                        enter = slideInVertically(spring(dampingRatio = 0.7f, stiffness = stiffness)) { -it },
                        exit = slideOutVertically { it }
                    ),
                    text = char.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    color = color
                )
            }
        }
    }
}

@Preview("Light Theme", showBackground = true)
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun TextTransitionPreview() {
    TextTransition(textAndColor = Pair("Hey Dude", Color.Cyan))
}
