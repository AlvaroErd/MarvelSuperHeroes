package com.alerdoci.marvelsuperheroes.app.common.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.FloatRange
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.common.extensions.ModifierExtensions.innerShadow
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors

object ModifierExtensions {

    private const val ShakeDurationMillis = 800

    //For clickable elements
    @SuppressLint("ModifierFactoryUnreferencedReceiver")
    fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
        clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick()
        }
    }

    //For boxes
    fun Modifier.shadow(
        spread: Dp = 8.dp,
        @FloatRange(from = 0.0, to = 1.0) alpha: Float = .25f,
        color: Color = Color.Gray,
        radius: Dp = 8.dp
    ): Modifier {
        val spreadLayer = spread.value.toInt()

        var modifier = this

        for (x in spreadLayer downTo 1) {
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = color.copy(alpha / x),
                    shape = RoundedCornerShape(radius + x.dp)
                )
                .padding(1.dp)
        }

        return modifier
    }

    fun Modifier.shaker(shakerController: ShakerController) = composed {
        shakerController.config?.let { config ->
            val shake = remember { Animatable(0f) }

            LaunchedEffect(shakerController.config) {
                for (i in 0 until config.iterations) {
                    shake.animateTo(1f, spring(stiffness = config.intensity))
                    shake.animateTo(-1f, spring(stiffness = config.intensity))
                }
                shake.animateTo(0f)
            }

            this.graphicsLayer {
                rotationX = shake.value * config.rotateX
                rotationY = shake.value * config.rotateY
                rotationZ = shake.value * config.rotateZ

                scaleX = 1f + (shake.value * config.scaleX)
                scaleY = 1f + (shake.value * config.scaleY)

                translationX = shake.value * config.translateX
                translationY = shake.value * config.translateY
            }
        } ?: this
    }

    @Composable
    fun rememberShakerState(): ShakerController {
        return remember { ShakerController() }
    }

    class ShakerController {
        var config: ShakeConfig? by mutableStateOf(null)
            private set

        fun shake(config: ShakeConfig) {
            this.config = config
        }
    }

    data class ShakeConfig(
        val iterations: Int,
        val intensity: Float = 1_000f,
        val rotateX: Float = 0f,
        val rotateY: Float = 0f,
        val rotateZ: Float = 0f,
        val scaleX: Float = 0f,
        val scaleY: Float = 0f,
        val translateX: Float = 0f,
        val translateY: Float = 0f,
        val trigger: Long = System.currentTimeMillis(),
    )

    fun Modifier.vibrateFeedback(
        duration: Long = 300L,
        isError: Boolean
    ) = composed {
        val context = LocalContext.current
        if (isError) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).vibrate(
                    CombinedVibration.createParallel(
                        VibrationEffect.createOneShot(
                            duration,
                            VibrationEffect.DEFAULT_AMPLITUDE,
                        ),
                    ),
                )
            } else {
                (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).let {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        it.vibrate(
                            VibrationEffect.createOneShot(
                                duration,
                                VibrationEffect.DEFAULT_AMPLITUDE,
                            ),
                        )
                    } else {
                        it.vibrate(duration)
                    }
                }
            }
        }
        this
    }

    @OptIn(ExperimentalFoundationApi::class)
    fun Modifier.hapticClickable(
        enabled: Boolean = true,
        hapticFeedbackEnabled: Boolean = true,
        onLongClick: (() -> Unit)? = null,
        onClick: () -> Unit
    ): Modifier {
        return if (hapticFeedbackEnabled) composed {
            // with haptic feedback
            val hapticFeedback = LocalHapticFeedback.current

            if (onLongClick != null) this.combinedClickable(
                enabled = enabled,
                onClick = {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    onClick()
                },
                onLongClick = {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    onLongClick()
                }
            ) else this.clickable(enabled = enabled) {
                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                onClick()
            }
        } else {
            // no haptic feedback
            if (onLongClick != null) this.combinedClickable(
                enabled = enabled,
                onClick = onClick,
                onLongClick = onLongClick,
            ) else this.clickable(enabled = enabled, onClick = onClick)
        }
    }

    fun Modifier.optionalClickable(onClick: (() -> Unit)?): Modifier {
        if (onClick == null) {
            return this
        }
        return clickable { onClick() }
    }

    @SuppressLint("ModifierFactoryUnreferencedReceiver")
    fun Modifier.hapticClickable(
        hapticFeedbackType: HapticFeedbackType = HapticFeedbackType.TextHandleMove,
        enabled: Boolean = true,
        onClick: (() -> Unit)
    ): Modifier {
        return composed {
            val hapticFeedback = LocalHapticFeedback.current
            clickable(enabled = enabled) {
                hapticFeedback.performHapticFeedback(hapticFeedbackType)
                onClick()
            }
        }
    }

    @SuppressLint("ModifierFactoryUnreferencedReceiver")
    fun Modifier.animatedHeight(height: Dp): Modifier {
        return composed {
            var previousValue by remember { mutableStateOf<Float?>(null) }
            val animatable = remember { Animatable(height.value) }
            LaunchedEffect(key1 = height, block = {
                if (previousValue == null || previousValue != height.value) {
                    previousValue = height.value
                    animatable.animateTo(height.value)
                }
            })
            height(animatable.value.dp)
        }
    }


    fun Modifier.advancedShadow(
        color: Color = Color.Black,
        alpha: Float = 0f,
        cornersRadius: Dp = 0.dp,
        shadowBlurRadius: Dp = 0.dp,
        offsetY: Dp = 0.dp,
        offsetX: Dp = 0.dp
    ) = drawBehind {

        val shadowColor = color.copy(alpha = alpha).toArgb()
        val transparentColor = color.copy(alpha = 0f).toArgb()

        drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = transparentColor
            frameworkPaint.setShadowLayer(
                shadowBlurRadius.toPx(),
                offsetX.toPx(),
                offsetY.toPx(),
                shadowColor
            )
            it.drawRoundRect(
                0f,
                0f,
                this.size.width,
                this.size.height,
                cornersRadius.toPx(),
                cornersRadius.toPx(),
                paint
            )
        }
    }

    fun Modifier.standardShadow(cornerRadius: Dp): Modifier = this.then(
        advancedShadow(
            color = Color.Black,
            alpha = 1f,
            cornersRadius = cornerRadius,
            shadowBlurRadius = 0.0001.dp,
            offsetX = 4.dp,
            offsetY = 4.dp
        )
    )

    fun Modifier.standardBackground(padding: Dp): Modifier = this.then(
        fillMaxSize()
            .background(MarvelColors.amber_500)
            .padding(all = padding)
    )

    fun Modifier.wideTextField(): Modifier = this.then(
        Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = MarvelColors.amber_500,
                shape = RoundedCornerShape(20.dp)
            )
            .advancedShadow(
                color = Color.Black,
                alpha = 1f,
                cornersRadius = 20.dp,
                shadowBlurRadius = 0.0001.dp,
                offsetX = 4.dp,
                offsetY = 4.dp
            )
    )

    @SuppressLint("ModifierFactoryUnreferencedReceiver")
    inline fun Modifier.noRippleClickableV2(crossinline onClick: () -> Unit): Modifier = composed {
        clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onClick()
        }
    }

    @Composable
    fun Modifier.animatedBorder(
        backgroundColor: Color = MaterialTheme.colorScheme.surface,
        borderWidth: Dp = 2.dp,
        gradient: Brush = Brush.sweepGradient(listOf(Color.Cyan, Color.Magenta)),
        animationDuration: Int = 10000,
    ) = composed {
        val infiniteTransition = rememberInfiniteTransition(label = "Infinite Color Animation")
        val degrees by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = animationDuration, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "Infinite Colors"
        )
        clipToBounds()
            .padding(borderWidth)
            .drawBehind {
                rotate(degrees = degrees) {
                    drawCircle(
                        brush = gradient,
                        radius = size.width,
                        blendMode = BlendMode.SrcIn,
                    )
                }
            }
            .background(backgroundColor)
    }

    fun Modifier.innerShadow(
        color: Color = Black,
        cornersRadius: Dp = 0.dp,
        spread: Dp = 0.dp,
        blur: Dp = 0.dp,
        offsetY: Dp = 0.dp,
        offsetX: Dp = 0.dp
    ) = drawWithContent {

        drawContent()

        val rect = Rect(Offset.Zero, size)
        val paint = Paint()

        drawIntoCanvas {

            paint.color = color
            paint.isAntiAlias = true
            it.saveLayer(rect, paint)
            it.drawRoundRect(
                left = rect.left,
                top = rect.top,
                right = rect.right,
                bottom = rect.bottom,
                cornersRadius.toPx(),
                cornersRadius.toPx(),
                paint
            )
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            if (blur.toPx() > 0) {
                frameworkPaint.maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
            }
            val left = if (offsetX > 0.dp) {
                rect.left + offsetX.toPx()
            } else {
                rect.left
            }
            val top = if (offsetY > 0.dp) {
                rect.top + offsetY.toPx()
            } else {
                rect.top
            }
            val right = if (offsetX < 0.dp) {
                rect.right + offsetX.toPx()
            } else {
                rect.right
            }
            val bottom = if (offsetY < 0.dp) {
                rect.bottom + offsetY.toPx()
            } else {
                rect.bottom
            }
            paint.color = Black
            it.drawRoundRect(
                left = left + spread.toPx() / 2,
                top = top + spread.toPx() / 2,
                right = right - spread.toPx() / 2,
                bottom = bottom - spread.toPx() / 2,
                cornersRadius.toPx(),
                cornersRadius.toPx(),
                paint
            )
            frameworkPaint.xfermode = null
            frameworkPaint.maskFilter = null
        }
    }

    fun Modifier.customShadow(
        color: Color = Black,
        borderRadius: Dp = 0.dp,
        blurRadius: Dp = 0.dp,
        spread: Dp = 0f.dp,
        widthOffset: Dp = 0.dp,
        heightOffset: Dp = 0.dp,
        offsetY: Dp = 0.dp,
        offsetX: Dp = 0.dp,
    ) = this.drawBehind {
        this.drawIntoCanvas {
            size.height
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx() - widthOffset.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx() - heightOffset.toPx()
            val rightPixel = (this.size.width + spreadPixel) + widthOffset.toPx()
            val bottomPixel = (this.size.height + spreadPixel) + heightOffset.toPx()

            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint
            )
        }
    }


    @SuppressLint("ModifierFactoryUnreferencedReceiver")
    fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed(
        factory = {
            val density = LocalDensity.current
            val strokeWidthPx = density.run { strokeWidth.toPx() }

            Modifier.drawWithContent {
                drawContent()
                val width = size.width
                val height = size.height - strokeWidthPx / 2

                drawLine(
                    color = color,
                    start = Offset(x = 0f, y = height),
                    end = Offset(x = width, y = height),
                    strokeWidth = strokeWidthPx
                )
            }
        }
    )

    @SuppressLint("ModifierFactoryUnreferencedReceiver")
    fun Modifier.topBorder(strokeWidth: Dp, color: Color) = composed(
        factory = {
            val density = LocalDensity.current
            val strokeWidthPx = density.run { strokeWidth.toPx() }

            Modifier.drawWithContent {
                drawContent()
                val width = size.width
                val height = strokeWidthPx / 2

                drawLine(
                    color = color,
                    start = Offset(x = 0f, y = height),
                    end = Offset(x = width, y = height),
                    strokeWidth = strokeWidthPx
                )
            }
        }
    )

    @SuppressLint("ModifierFactoryUnreferencedReceiver")
    fun Modifier.leftBorder(strokeWidth: Dp, color: Color) = composed(
        factory = {
            val density = LocalDensity.current
            val strokeWidthPx = density.run { strokeWidth.toPx() }

            Modifier.drawWithContent {
                drawContent()
                val width = size.width
                val height = size.height - strokeWidthPx / 2

                drawLine(
                    color = color,
                    start = Offset(x = 0f, y = 0f),
                    end = Offset(x = 0f, y = height),
                    strokeWidth = strokeWidthPx
                )
            }
        }
    )

    @SuppressLint("ModifierFactoryUnreferencedReceiver")
    fun Modifier.rightBorder(strokeWidth: Dp, color: Color) = composed(
        factory = {
            val density = LocalDensity.current
            val strokeWidthPx = density.run { strokeWidth.toPx() }

            Modifier.drawWithContent {
                drawContent()
                val width = size.width
                val height = size.height - strokeWidthPx / 2

                drawLine(
                    color = color,
                    start = Offset(x = width, y = 0f),
                    end = Offset(x = width, y = height),
                    strokeWidth = strokeWidthPx
                )
            }
        }
    )

    fun Modifier.clickableOrNull(clickable: Boolean?, onClick: () -> Unit): Modifier {
        return if (clickable != null) {
            this.then(
                Modifier.clickable(
                    enabled = clickable
                ) {
                    onClick()
                }
            )
        } else {
            this
        }
    }

}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}

@Preview
@Composable
fun CardScreen(
    cardModifier: Modifier = Modifier,
    screenModifier: Modifier = Modifier,
    cardPadding: PaddingValues = PaddingValues(10.dp),
    cardBorderSize: PaddingValues = PaddingValues(10.dp),
    cardCorner: Dp = 8.dp,
    cardInternBackground: Color = Blue,
    screenCorner: Dp = 6.dp,
//    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(cardPadding)
            .then(cardModifier),
        color = Gray,
        shape = RoundedCornerShape(cardCorner)
    ) {
        Surface(
            modifier = Modifier
                .padding(cardBorderSize)
                .innerShadow(
                    color = Black,
                    cornersRadius = screenCorner,
                    blur = 5.dp
                )
                .then(screenModifier),
            color = cardInternBackground,
            shape = RoundedCornerShape(screenCorner)
        ) {
            Image(
                painter = painterResource(id = R.drawable.groot_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}
