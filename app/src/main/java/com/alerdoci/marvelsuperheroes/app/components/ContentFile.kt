package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark

@Composable
fun ContentTitle(modifier: Modifier = Modifier, text: String, animate: Boolean) {
    AnimatedText(
        modifier = modifier,
        text = text,
        useAnimation = animate,
        fontFamily = FontFamily.Monospace,
        textColor = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.primary),
    )
}

@Preview
@PreviewLightDark
@Composable
fun PreviewContentTitle() {
    ContentTitle(text = "Hola", animate = true)
}