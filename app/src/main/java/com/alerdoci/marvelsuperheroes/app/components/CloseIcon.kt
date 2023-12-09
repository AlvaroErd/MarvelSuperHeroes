package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CloseIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors()
) {
    IconButton(onClick = onClick, modifier = modifier, colors = colors) {
        Icon(Icons.Default.Close, contentDescription = "Close")
    }
}

@Preview
@Composable
fun CloseIconPreview(){
    CloseIcon({

    })
}
