package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SegmentPathDivider(
    modifier: Modifier = Modifier,
    width: Dp = 20.dp,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color,
) = Canvas(
    modifier
        .fillMaxHeight()
        .width(width)) {
    drawLine(
        color = color,
        strokeWidth = thickness.toPx(),
        start = Offset(thickness.toPx() / 2 + width.toPx() * 0.25f, size.height),
        end = Offset(thickness.toPx() / 2 + width.toPx() * 0.75f, 0f)
    )
}

@Preview
@Composable
fun Demo_SegmentPathDivider() {
    val pathSegments = listOf("Projects", "Important project", "src")
    Row(
        modifier = Modifier
            .padding(16.dp)
            .height(IntrinsicSize.Min)
    ) {
        pathSegments.forEachIndexed { index, segment ->
            Text(text = segment, fontSize = 20.sp)
            if (index < pathSegments.size - 1) {
                SegmentPathDivider(
                    color = Color.Black,
                    thickness = 2.dp
                )
            }
        }
    }
}
