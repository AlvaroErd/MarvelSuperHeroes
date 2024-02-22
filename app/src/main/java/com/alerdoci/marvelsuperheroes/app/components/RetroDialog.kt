package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun RetroDialog(
    title: String,
    message: String,
    backgroundColor: Color = Color(0xFFCCCCCC),
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            Modifier
                .clip(RectangleShape)
                .fillMaxWidth()
                .background(backgroundColor)
        ) {
            Column {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.Blue)
                        .padding(start = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(title, color = Color.White, fontFamily = FontFamily.Monospace)
                    Surface(
                        onClick = onDismissRequest, shape = RectangleShape,
                        modifier = Modifier.padding(2.dp),
                        color = backgroundColor
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Row(
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 20.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        Icons.Filled.Info,
                        contentDescription = "Error",
                        tint = Color.Red,
                        modifier = Modifier.size(48.dp)
                    )

                    Text(message, fontFamily = FontFamily.Monospace)
                }
                Surface(
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 10.dp),
                    onClick = onDismissRequest,
                    shape = RectangleShape,
                    color = backgroundColor,
                    border = BorderStroke(Dp.Hairline, Color.Black),
                ) {
                    Text(
                        "OK", fontFamily = FontFamily.Monospace,
                        modifier = Modifier
                            .widthIn(120.dp)
                            .padding(vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RetroDialogPreview(){
    var isDialogOpened by remember { mutableStateOf(true) }

    RetroDialog(
        title = "System Failure",
        message= "Error not found",
        onDismissRequest = { isDialogOpened = false }
    )
}
