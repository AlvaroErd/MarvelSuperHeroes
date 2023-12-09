package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextFieldSizeControled() {

    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = {
            if (it.length < 6) {
                text = it
            }
        },
        enabled = text.length < 6
    )
}

@Preview(showBackground = true)
@Composable
fun TextFieldSizeControledPreview() {
    TextFieldSizeControled()
}
