package com.alerdoci.marvelsuperheroes.app.components.jelly

import android.graphics.PointF

data class Line(
    val firstsPoint: PointF,
    val secondPoint: PointF,
)

fun findIntersection(l1: Line, l2: Line): PointF {
    val a1: Float = l1.secondPoint.y - l1.firstsPoint.y
    val b1: Float = l1.firstsPoint.x - l1.secondPoint.x
    val c1: Float = a1 * l1.firstsPoint.x + b1 * l1.firstsPoint.y
    val a2: Float = l2.secondPoint.y - l2.firstsPoint.y
    val b2: Float = l2.firstsPoint.x - l2.secondPoint.x
    val c2: Float = a2 * l2.firstsPoint.x + b2 * l2.firstsPoint.y
    val delta = a1 * b2 - a2 * b1
    return PointF(((b2 * c1 - b1 * c2) / delta), ((a1 * c2 - a2 * c1) / delta))
}
