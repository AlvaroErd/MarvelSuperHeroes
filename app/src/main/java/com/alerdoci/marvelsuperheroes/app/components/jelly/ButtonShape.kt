package com.alerdoci.marvelsuperheroes.app.components.jelly

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class ButtonShape(
    private val cornerRadius: Float,
    private val heightAnimation: Float
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().addRoundRectWithSpecialStartPath(
            RoundRect(
                Rect(Offset.Zero, size),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            ),
            heightAnimation = heightAnimation
        )
        return Outline.Generic(
            path = path
        )
    }
}
