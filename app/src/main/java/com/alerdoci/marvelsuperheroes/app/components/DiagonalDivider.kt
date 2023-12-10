package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Path
import com.alerdoci.marvelsuperheroes.app.common.annotations.FullPreview
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.red_800

@FullPreview
@Composable
fun DiagonalDivider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                val width = size.width
                val height = size.height
                val path = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(width, 0f)
                    lineTo(width * 0f, height)
                    lineTo(0f, height)
                    close()
                }
                drawPath(path, color = red_800)
            },
    ) {
    }
}
