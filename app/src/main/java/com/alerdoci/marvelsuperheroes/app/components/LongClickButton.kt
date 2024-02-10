package com.alerdoci.marvelsuperheroes.app.components

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LongClickButton() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = {
                Toast.makeText(context, "Normal Click", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .combinedClickable(
                    onClick = {
                        Toast.makeText(context, "Combined Click", Toast.LENGTH_SHORT).show()
                    },
                    onLongClick = {
                        Toast.makeText(context, "Combined Long Click", Toast.LENGTH_SHORT).show()
                    },
                )
        ) {
            Text("Button")
        }

        IconButton(
            onClick = {
                Toast.makeText(context, "Normal Click", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .combinedClickable(
                    onClick = {
                        Toast.makeText(context, "Combined Click", Toast.LENGTH_SHORT).show()
                    },
                    onLongClick = {
                        Toast.makeText(context, "Combined Long Click", Toast.LENGTH_SHORT).show()
                    },
                )
        ) {
            Icon(Icons.Default.ArrowBack, null)
        }
    }
}
