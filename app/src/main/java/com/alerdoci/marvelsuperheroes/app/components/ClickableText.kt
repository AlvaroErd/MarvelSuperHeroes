package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomClickableText() {
    val uriTag = "URI"
    val uriHandler = LocalUriHandler.current
    val annotatedString = buildAnnotatedString {
        append("Alvaro Erdociain (Github)")
        addStyle(
            style = SpanStyle(textDecoration = TextDecoration.Underline),
            start = 18,
            end = 24
        )
        addStringAnnotation(
            tag = uriTag,
            annotation = "https://github.com/alvaroerd",
            start = 18,
            end = 24
        )
    }

    ClickableText(
        text = annotatedString,
        onClick = { position ->
            val annotations = annotatedString.getStringAnnotations(
                uriTag,
                start = position,
                end = position
            )
            annotations.firstOrNull()?.let {
                uriHandler.openUri(it.item)
            }
        },
        style = TextStyle(fontSize = 24.sp),
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
@Preview(showBackground = true)
fun CustomClickableTextPreview() {
    CustomClickableText()
}
