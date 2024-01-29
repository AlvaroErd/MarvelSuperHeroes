package com.alerdoci.marvelsuperheroes.app.components.jelly

import android.graphics.PointF
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Path

fun Path.addRoundRectWithSpecialStartPath(
    roundRect: RoundRect,
    heightAnimation: Float
): Path {
    val width = roundRect.width
    val height = roundRect.height
    val startY = height / 3
    val endY = 2 * height / 3

    return apply {

        moveTo(roundRect.topLeftCornerRadius.x, startY)
        val x1 = (width - roundRect.topRightCornerRadius.x) / 3
        cubicTo(
            x1 = x1,
            x2 = x1 * 2,
            x3 = width - roundRect.topRightCornerRadius.x,
            y1 = heightAnimation + startY,
            y2 = heightAnimation + startY,
            y3 = startY,
        )

        var point = findIntersection(
            Line(
                PointF(x1 * 2, heightAnimation + startY),
                PointF(width - roundRect.topRightCornerRadius.x, startY)
            ),
            Line(
                PointF(width, roundRect.topRightCornerRadius.y + startY),
                PointF(width, endY)
            )
        )

        quadraticBezierTo(
            x1 = point.x,
            y1 = point.y,
            x2 = width,
            y2 = roundRect.topRightCornerRadius.y + startY
        )

        lineTo(width, endY - roundRect.bottomRightCornerRadius.y)


        val x2 =
            (width - roundRect.bottomRightCornerRadius.x - roundRect.bottomLeftCornerRadius.x) / 3

        point = findIntersection(
            Line(
                PointF(width, roundRect.topRightCornerRadius.y + startY),
                PointF(width, endY - roundRect.bottomRightCornerRadius.y)
            ),
            Line(
                PointF(roundRect.bottomLeftCornerRadius.x + (x2 * 2), endY + heightAnimation),
                PointF(width - roundRect.bottomLeftCornerRadius.x, endY)
            )
        )

        quadraticBezierTo(
            x1 = point.x,
            y1 = point.y,
            x2 = width - roundRect.bottomRightCornerRadius.x,
            y2 = endY
        )

        cubicTo(
            x1 = roundRect.bottomLeftCornerRadius.x + x2 * 2,
            x2 = roundRect.bottomLeftCornerRadius.x + x2,
            x3 = roundRect.bottomLeftCornerRadius.x,
            y1 = heightAnimation + endY,
            y2 = heightAnimation + endY,
            y3 = endY,
        )

        point = findIntersection(
            Line(
                PointF(0f, roundRect.topLeftCornerRadius.y + startY),
                PointF(0f, endY - roundRect.bottomRightCornerRadius.y)
            ),
            Line(
                PointF(roundRect.bottomLeftCornerRadius.x, endY),
                PointF(roundRect.bottomLeftCornerRadius.x + x2, endY + heightAnimation)
            )
        )

        quadraticBezierTo(
            x1 = point.x,
            y1 = point.y,
            x2 = 0f,
            y2 = endY - roundRect.bottomLeftCornerRadius.y
        )

        lineTo(0F, startY + roundRect.topLeftCornerRadius.y)

        point = findIntersection(
            Line(
                PointF(0f, roundRect.topLeftCornerRadius.y + startY),
                PointF(0f, endY - roundRect.bottomLeftCornerRadius.y)
            ),
            Line(
                PointF(roundRect.bottomLeftCornerRadius.x, startY),
                PointF(roundRect.bottomLeftCornerRadius.x + x2, startY + heightAnimation)
            )
        )

        quadraticBezierTo(
            x1 = point.x,
            y1 = point.y,
            x2 = roundRect.topLeftCornerRadius.x,
            y2 = startY
        )

        close()
    }
}
