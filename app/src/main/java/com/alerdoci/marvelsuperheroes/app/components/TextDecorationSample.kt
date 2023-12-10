package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun TextDecorationSample() {
    var isCompleted by remember { mutableStateOf(false) }

    Column {
        Button(onClick = { isCompleted = !isCompleted }) {
            Text(text = "Button")
        }
        CheckTextDecoration(task = isCompleted)

    }
}

@Composable
fun CheckTextDecoration(task: Boolean) {
    Box(modifier = Modifier.fillMaxSize()){
        Text(
            text = "Metehan",
            color = Color.Red,
            textDecoration = if (task) {
                TextDecoration.LineThrough + TextDecoration.Underline
            } else {
                null
            }
        )

    }
}
