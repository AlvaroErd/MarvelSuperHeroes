package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview()
@Composable
fun Card() {
    var rotationX by remember { mutableStateOf(0f) }
    var rotationY by remember { mutableStateOf(0f) }
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)) {
        Box(
            modifier = Modifier
                .size(300.dp, 200.dp)
                .align(Alignment.Center)
                .blur(5.dp, 5.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            Color(0xFF00FFFF),
                            Color(0xFFFF00FF),
                            Color(0xFFFFFF00),
                            Color(0xFF00FFFF)
                        ),
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
        )
        Box(
            modifier = Modifier
                .size(300.dp, 200.dp)
                .graphicsLayer {
                    this.rotationX = rotationX
                    this.rotationY = rotationY
                }
                .align(Alignment.Center)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Black)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        val rotationYI = (0f..300.dp.toPx()).convert(change.position.x, (0f..1f))
                        val rotationXI = (0f..200.dp.toPx()).convert(change.position.y, (0f..1f))
                        rotationX = lerp2(6f, -6f, rotationXI)
                        rotationY = lerp2(-3f, 3f, rotationYI)
                    }
                }
        ){
            Row(modifier = Modifier.padding(20.dp).align(Alignment.BottomStart), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Box(
                    Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(0.4f)))
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(
                        Modifier
                            .size(120.dp, 15.dp)
                            .clip(CircleShape)
                            .background(Color.Gray.copy(0.4f)))
                    Box(
                        Modifier
                            .size(60.dp, 15.dp)
                            .clip(CircleShape)
                            .background(Color.Gray.copy(0.4f)))
                }
            }
        }
    }
}

fun lerp2(start: Float, stop: Float, fraction: Float) =
    (start * (1 - fraction) + stop * fraction)

fun ClosedFloatingPointRange<Float>.convert(number: Float, target: ClosedFloatingPointRange<Float>): Float {
    val ratio = number / (endInclusive - start)
    return ratio * (target.endInclusive - target.start)
}
