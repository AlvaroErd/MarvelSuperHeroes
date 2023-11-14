package com.alerdoci.marvelsuperheroes.app.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder

@Preview(name = "First", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Second", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun ShimmerEffect() {
    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        Text(
            text = "Hello from the other side.",
            modifier = Modifier
                .padding(16.dp)
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.shimmer(),
                    shape = RoundedCornerShape(10.dp),
                    color = Color.LightGray
                )
        )

    }
}




