@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.wear.compose.material.ContentAlpha
import androidx.wear.compose.material.LocalContentAlpha
import com.alerdoci.marvelsuperheroes.R


@Composable
fun NoPaddingAlertDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable (() -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties()
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Surface(
            modifier = modifier,
            shape = shape,
            color = backgroundColor,
            contentColor = contentColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                title?.let {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                        val textStyle = MaterialTheme.typography.bodyLarge
                        ProvideTextStyle(textStyle, it)
                    }
                }
                text?.let {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                        val textStyle = MaterialTheme.typography.bodyMedium
                        ProvideTextStyle(textStyle, it)
                    }
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        dismissButton?.invoke()
                        confirmButton()
                    }
                }
            }
        }
    }
}

@Composable
fun ConfirmDialog(
    cancelable: Boolean = true,
    title: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {},
    onYesNoClick: (Boolean) -> Unit
) {
    NoPaddingAlertDialog(
        onDismissRequest = { onYesNoClick(false) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        title = title,
        text = content,
        shape = MaterialTheme.shapes.medium,
        properties = DialogProperties(dismissOnClickOutside = cancelable),
        confirmButton = {
            Button(
                onClick = { onYesNoClick(true) },
                modifier = Modifier.padding(end = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = stringResource(id = R.string.yes),
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        },
        dismissButton = {
            Button(
                onClick = { onYesNoClick(false) },
                modifier = Modifier.padding(end = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(
                    text = stringResource(id = R.string.no),
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.bodyMedium,

                    )
            }
        }
    )
}