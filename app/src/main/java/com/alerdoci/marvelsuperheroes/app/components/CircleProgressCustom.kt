package com.alerdoci.marvelsuperheroes.app.components

import android.content.res.Configuration
import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.min

@Composable
internal fun CircleProgressCustom(
    modifier: Modifier = Modifier,
    baseColor: Color = MaterialTheme.colorScheme.surface,
    progressColor: Color = MaterialTheme.colorScheme.primary,
    allSteps: Int,
    finishedSteps: Int,
    sizeStroke: Dp,
    textColor: Color = Color.White,
    textFontSize: Dp = 20.dp
) {
    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
        val canvasSize = min(a = constraints.maxWidth, b = constraints.maxHeight)
        val size = Size(width = canvasSize.toFloat(), height = canvasSize.toFloat())
        val canvasSizeDp = with(receiver = LocalDensity.current) { canvasSize.toDp() }
        val progressArch = finishedSteps * 360f / allSteps
        val sliceWidthPx = with(LocalDensity.current) { sizeStroke.toPx() }
        val textFontSize = with(LocalDensity.current) { textFontSize.toPx() }

        Canvas(modifier = Modifier.size(canvasSizeDp)) {
            // Draw base with full process
            drawArc(
                color = baseColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                size = size,
                style = Stroke(width = sliceWidthPx)
            )
            // Draw finished process
            drawArc(
                color = progressColor,
                startAngle = 270f,
                sweepAngle = progressArch,
                useCenter = false,
                size = size,
                style = Stroke(width = sliceWidthPx, cap = StrokeCap.Round)
            )

            // text Paint with style
            val textPaint =
                Paint().apply {
                    color = textColor.toArgb()
                    textSize = textFontSize
                    textAlign = Paint.Align.CENTER
                    typeface = Typeface.DEFAULT_BOLD
                }

            // draw text at center
            drawIntoCanvas { canvas ->
                canvas.nativeCanvas.drawText(
                    "${finishedSteps}/${allSteps}",
                    (canvasSize / 2).toFloat(),
                    (canvasSize / 2) + textFontSize / 2,
                    textPaint
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun CircleProgressCustomPreview(darkTheme: Boolean = isSystemInDarkTheme()) {
    CircleProgressCustom(
        modifier = Modifier.size(92.dp),
        baseColor = (if (darkTheme) Color(0xFFD8DCF5) else Color(0xFFFEEAD9)),
        progressColor = (if (darkTheme) Color(0xFF2295F8) else Color(0xFFF76400)),
        allSteps = 20,
        finishedSteps = 10,
        sizeStroke = 10.dp,
        textColor = (if (darkTheme) Color(0xFF2295F8) else Color(0xFFF76400)),
        textFontSize = 20.dp,
    )
}

@Preview
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable

fun CourseItem(darkTheme: Boolean = isSystemInDarkTheme()) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(if (darkTheme) Color.Black else Color.White)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 32.dp,
                    bottom = 20.dp
                )
                .width(IntrinsicSize.Max)
        ) {
            CircleProgressCustom(
                modifier = Modifier.size(92.dp),
                baseColor = (if (darkTheme) Color(0xFFD8DCF5) else Color(0xFFFEEAD9)),
                progressColor = (if (darkTheme) Color(0xFF2295F8) else Color(0xFFF76400)),
                allSteps = 20,
                finishedSteps = 10,
                sizeStroke = 10.dp,
                textColor = (if (darkTheme) Color(0xFF2295F8) else Color(0xFFF76400)),
                textFontSize = 20.dp,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Spanish \nLanguage",
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
                color = (if (darkTheme) Color(0xFF2295F8) else Color(0xFFF76400))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "20 Classes •\t Easy",
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
                color = (if (darkTheme) Color(0xFF2295F8) else Color(0xFFF76400)).copy(alpha = 0.6f)
            )
        }
    }
}
