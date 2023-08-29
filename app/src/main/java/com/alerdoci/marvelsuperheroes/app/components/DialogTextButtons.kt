package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alerdoci.marvelsuperheroes.R

@Composable
fun DialogTextButtons(
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        TextButton(
            onClick = onCancelClick,
        ) {
            Text(text = stringResource(id = R.string.cancel))
        }
        TextButton(
            onClick = { onSaveClick() },
        ) {
            Text(text = stringResource(id = R.string.save),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}