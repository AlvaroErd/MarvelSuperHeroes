package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawContext
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random
import kotlin.math.roundToInt

/**
 * Designed as a wrapper around [TextLayoutResult] to optimize [drawGlyphs] for repeated bounding
 * box queries.
 */
sealed interface Glyphs {

  /**
   * How many glyphs are represented by this object.
   */
  val length: Int
}

/**
 * A helper function to directly measure [Glyphs], instead of using [TextLayoutResult.toGlyphs].
 */
@OptIn(ExperimentalTextApi::class)
fun TextMeasurer.measureGlyphs(
  text: AnnotatedString,
  style: TextStyle = TextStyle.Default,
  overflow: TextOverflow = TextOverflow.Clip,
  softWrap: Boolean = true,
  maxLines: Int = Int.MAX_VALUE,
  placeholders: List<AnnotatedString.Range<Placeholder>> = emptyList(),
  constraints: Constraints = Constraints(),
  skipCache: Boolean = false
): Glyphs {
  return measure(
    text = text,
    style = style,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    placeholders = placeholders,
    constraints = constraints,
    skipCache = skipCache
  ).toGlyphs()
}

/**
 * A helper function to directly measure [Glyphs], instead of using [TextLayoutResult.toGlyphs].
 */
@OptIn(ExperimentalTextApi::class)
fun TextMeasurer.measureGlyphs(
  text: String,
  style: TextStyle = TextStyle.Default,
  overflow: TextOverflow = TextOverflow.Clip,
  softWrap: Boolean = true,
  maxLines: Int = Int.MAX_VALUE,
  placeholders: List<AnnotatedString.Range<Placeholder>> = emptyList(),
  constraints: Constraints = Constraints(),
  skipCache: Boolean = false
): Glyphs {
  return measure(
    text = AnnotatedString(text),
    style = style,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    placeholders = placeholders,
    constraints = constraints,
    skipCache = skipCache
  ).toGlyphs()
}

/**
 * Converts a [TextLayoutResult] to its [Glyphs] representation to be used by [drawGlyphs].
 */
fun TextLayoutResult.toGlyphs(): Glyphs {
  return GlyphsImpl(this)
}

internal class GlyphsImpl internal constructor(
  private val textLayoutResult: TextLayoutResult
): Glyphs {
  override val length: Int
    get() = textLayoutResult.layoutInput.text.length

  val boundingBoxes: List<Rect>

  val glyphDrawScopes: List<GlyphDrawScopeImpl>

  init {
    boundingBoxes = (0 until length).map {
      textLayoutResult.getBoundingBox(it)
    }

    glyphDrawScopes = (0 until length).map {
      GlyphDrawScopeImpl(
        it, textLayoutResult, boundingBoxes[it]
      )
    }
  }
}

/**
 * Special [DrawScope] provided by [drawGlyphs] to decide how to draw each glyph individually in a
 * text layout.
 */
interface GlyphDrawScope : DrawScope {

  /**
   * Index of the glyph that is referenced by this scope in its original text.
   */
  val offset: Int

  /**
   * Draws the glyph referenced by this scope.
   */
  fun drawGlyph(
    color: Color = Color.Unspecified,
    alpha: Float = Float.NaN,
    shadow: Shadow = Shadow.None
  )

  /**
   * Draws the glyph referenced by this scope.
   */
  fun drawGlyph(
    brush: Brush,
    alpha: Float = Float.NaN,
    shadow: Shadow = Shadow.None
  )
}


internal class GlyphDrawScopeImpl(
  override val offset: Int,
  private val textLayoutResult: TextLayoutResult,
  private val box: Rect
) : GlyphDrawScope {
  private var backingDrawScope: DrawScope? = null

  fun withDrawScope(drawScope: DrawScope, block: GlyphDrawScope.() -> Unit) {
    backingDrawScope = drawScope
    block()
    backingDrawScope = null
  }

  private val boundingBox = box.translate(Offset(-box.left, -box.top))

  /**
   * Overriding center for animations under [GlyphDrawScope] to use bounding box's center
   * by default.
   */
  override val center: Offset = boundingBox.center

  /**
   * Overriding size for animations under [GlyphDrawScope] to use bounding box's size
   * by default.
   */
  override val size: Size = boundingBox.size

  override fun drawGlyph(
    color: Color,
    alpha: Float,
    shadow: Shadow
  ) {
    withTransform({
      val box = this@GlyphDrawScopeImpl.box
      translate(left = -box.left, top = -box.top)
      clipRect(
        left = box.left,
        top = box.top,
        right = box.right,
        bottom = box.bottom
      )
    }) {
      drawText(
        textLayoutResult = this@GlyphDrawScopeImpl.textLayoutResult,
        alpha = alpha,
        color = color,
        shadow = shadow
      )
    }
  }

  override fun drawGlyph(
    brush: Brush,
    alpha: Float,
    shadow: Shadow
  ) {
    withTransform({
      val box = this@GlyphDrawScopeImpl.box
      translate(left = -box.left, top = -box.top)
      clipRect(
        left = box.left,
        top = box.top,
        right = box.right,
        bottom = box.bottom
      )
    }) {
      drawText(
        textLayoutResult = this@GlyphDrawScopeImpl.textLayoutResult,
        alpha = alpha,
        brush = brush,
        shadow = shadow
      )
    }
  }

  //region DrawScope
  override val density: Float
    get() = backingDrawScope?.density ?: error("Backing scope not specified")
  override val drawContext: DrawContext
    get() = backingDrawScope?.drawContext ?: error("Backing scope not specified")
  override val fontScale: Float
    get() = backingDrawScope?.fontScale ?: error("Backing scope not specified")
  override val layoutDirection: LayoutDirection
    get() = backingDrawScope?.layoutDirection ?: error("Backing scope not specified")

  override fun drawArc(
    brush: Brush,
    startAngle: Float,
    sweepAngle: Float,
    useCenter: Boolean,
    topLeft: Offset,
    size: Size,
    alpha: Float,
    style: DrawStyle,
    colorFilter: ColorFilter?,
    blendMode: BlendMode
  ) {
    backingDrawScope?.drawArc(
      brush,
      startAngle,
      sweepAngle,
      useCenter,
      topLeft,
      size,
      alpha,
      style,
      colorFilter,
      blendMode
    ) ?: error("Backing scope not specified")
  }

  override fun drawArc(
    color: Color,
    startAngle: Float,
    sweepAngle: Float,
    useCenter: Boolean,
    topLeft: Offset,
    size: Size,
    alpha: Float,
    style: DrawStyle,
    colorFilter: ColorFilter?,
    blendMode: BlendMode
  ) {
    backingDrawScope?.drawArc(
      color,
      startAngle,
      sweepAngle,
      useCenter,
      topLeft,
      size,
      alpha,
      style,
      colorFilter,
      blendMode
    ) ?: error("Backing scope not specified")
  }

  override fun drawCircle(
    brush: Brush,
    radius: Float,
    center: Offset,
    alpha: Float,
    style: DrawStyle,
    colorFilter: ColorFilter?,
    blendMode: BlendMode
  ) {
    backingDrawScope?.drawCircle(brush, radius, center, alpha, style, colorFilter, blendMode)
      ?: error("Backing scope not specified")
  }

  override fun drawCircle(
    color: Color,
    radius: Float,
    center: Offset,
    alpha: Float,
    style: DrawStyle,
    colorFilter: ColorFilter?,
    blendMode: BlendMode
  ) {
    backingDrawScope?.drawCircle(color, radius, center, alpha, style, colorFilter, blendMode)
      ?: error("Backing scope not specified")
  }

  override fun drawImage(
    image: ImageBitmap,
    topLeft: Offset,
    alpha: Float,
    style: DrawStyle,
    colorFilter: ColorFilter?,
    blendMode: BlendMode
  ) {
    backingDrawScope?.drawImage(image, topLeft, alpha, style, colorFilter, blendMode)
      ?: error("Backing scope not specified")
  }

  override fun drawImage(
    image: ImageBitmap,
    srcOffset: IntOffset,
    srcSize: IntSize,
    dstOffset: IntOffset,
    dstSize: IntSize,
    alpha: Float,
    style: DrawStyle,
    colorFilter: ColorFilter?,
    blendMode: BlendMode
  ) {
    backingDrawScope?.drawImage(
      image,
      srcOffset,
      srcSize,
      dstOffset,
      dstSize,
      alpha,
      style,
      colorFilter,
      blendMode
    ) ?: error("Backing scope not specified")
  }

  override fun drawLine(
    brush: Brush,
    start: Offset,
    end: Offset,
    strokeWidth: Float,
    cap: StrokeCap,
    pathEffect: PathEffect?,
    alpha: Float,
    colorFilter: ColorFilter?,
    blendMode: BlendMode
  ) {
    backingDrawScope?.drawLine(
      brush,
      start,
      end,
      strokeWidth,
      cap,
      pathEffect,
      alpha,
      colorFilter,
      blendMode
    ) ?: error("Backing scope not specified")
  }

  override fun drawLine(
    color: Color,
    start: Offset,
    end: Offset,
    strokeWidth: Float,
    cap: StrokeCap,
    pathEffect: PathEffect?,
    alpha: Float,
    colorFilter: ColorFilter?,
    blendMode: BlendMode
  ) {
    backingDrawScope?.drawLine(
      color,
      start,
      end,
      strokeWidth,
      cap,
      pathEffect,
      alpha,
      colorFilter,
      blendMode
    ) ?: error("Backing scope not specified")
  }

  override fun drawOval(
    brush: Brush,
    topLeft: Offset,
    size: Size,
    alpha: Float,
    style: DrawStyle,
    colorFilter: ColorFilter?,
    blendMode: BlendMode
  ) {
    backingDrawScope?.drawOval(brush, topLeft, size, alpha, style, colorFilter, blendMode) ?: error(
      "Backing scope not specified"
    )
  }

  override fun drawOval(
    color: Color,
    topLeft: Offset,
    size: Size,
    alpha: Float,
    style: DrawStyle,
    colorFilter: ColorFilter?,
    blendMode: BlendMode
  ) {
    backingDrawScope?.drawOval(color, topLeft, size, alpha, style, colorFilter, blendMode) ?: error(
      "Backing scope not specified"
    )
  }

  override fun drawPath(
    path: Path,
    brush: Brush,
    alpha: Float,
    style: DrawStyle,
    colorFilter: ColorFilter?,
    blendMode: BlendMode
  ) {
    backingDrawScope?.drawPath(path, brush, alpha, style, colorFilter, blendMode)
      ?: error("Backing scope not specified")
  }

  override fun drawPath(
    path: Path,
    color: Color,
    alpha: Float,
    style: DrawStyle,
    colorFilter: ColorFilter?,
    blendMode: BlendMode
  ) {
    backingDrawScope?.drawPath(path, color, alpha, style, colorFilter, blendMode)
      ?: error("Backing scope not specified")
  }

  override fun drawPoints(
    points: List<Offset>,
    pointMode: PointMode,
    brush: Brush,
    strokeWidth: Float,
    cap: StrokeCap,
    pathEffect: PathEffect?,
    alpha: Float,
    colorFilter: ColorFilter?,
    blendMode: BlendMode
  ) {
    backingDrawScope?.drawPoints(
      points,
      pointMode,
      brush,
      strokeWidth,
      cap,
      pathEffect,
      alpha,
      colorFilter,
      blendMode
    ) ?: error("Backing scope not specified")
  }

  override fun drawPoints(
    points: List<Offset>,
    pointMode: PointMode,
    color: Color,
    strokeWidth: Float,
    cap: StrokeCap,
    pathEffect: PathEffect?,
    alpha: Float,
    colorFilter: ColorFilter?,
    blendMode: BlendMode
  ) {
    backingDrawScope?.drawPoints(
      points,
      pointMode,
      color,
      strokeWidth,
      cap,
      pathEffect,
      alpha,
      colorFilter,
      blendMode
    ) ?: error("Backing scope not specified")
  }

  override fun drawRect(
    brush: Brush,
    topLeft: Offset,
    size: Size,
    alpha: Float,
    style: DrawStyle,
    colorFilter: ColorFilter?,
    blendMode: BlendMode
  ) {
    backingDrawScope?.drawRect(brush, topLeft, size, alpha, style, colorFilter, blendMode) ?: error(
      "Backing scope not specified"
    )
  }

  override fun drawRect(
    color: Color,
    topLeft: Offset,
    size: Size,
    alpha: Float,
    style: DrawStyle,
    colorFilter: ColorFilter?,
    blendMode: BlendMode
  ) {
    backingDrawScope?.drawRect(color, topLeft, size, alpha, style, colorFilter, blendMode) ?: error(
      "Backing scope not specified"
    )
  }

  override fun drawRoundRect(
    brush: Brush,
    topLeft: Offset,
    size: Size,
    cornerRadius: CornerRadius,
    alpha: Float,
    style: DrawStyle,
    colorFilter: ColorFilter?,
    blendMode: BlendMode
  ) {
    backingDrawScope?.drawRoundRect(
      brush,
      topLeft,
      size,
      cornerRadius,
      alpha,
      style,
      colorFilter,
      blendMode
    ) ?: error("Backing scope not specified")
  }

  override fun drawRoundRect(
    color: Color,
    topLeft: Offset,
    size: Size,
    cornerRadius: CornerRadius,
    style: DrawStyle,
    alpha: Float,
    colorFilter: ColorFilter?,
    blendMode: BlendMode
  ) {
    backingDrawScope?.drawRoundRect(
      color,
      topLeft,
      size,
      cornerRadius,
      style,
      alpha,
      colorFilter,
      blendMode
    ) ?: error("Backing scope not specified")
  }
  //endregion
}

/**
 * Draws glyphs that exist in a [TextLayoutResult].
 *
 * A [TextLayoutResult] must be converted into a [Glyphs] in a [drawWithCache] modifier to
 * run this draw method efficiently.
 *
 * @param glyphs Glyphs object that defines each glyphs that's going to be drawn
 * @param autoTranslate Whether to translate the canvas for each GlyphDrawScope call so they are
 * placed at their original position in the TextLayoutResult.
 * @param block Draw block. Do not forget to call [GlyphDrawScope.drawGlyph]!
 */
fun DrawScope.drawGlyphs(
  glyphs: Glyphs,
  autoTranslate: Boolean = true,
  block: GlyphDrawScope.() -> Unit,
) {
  glyphs as? GlyphsImpl
    ?: error("Glyphs must be initialized with TextMeasurer.measureGlyphs() or " +
        "TextLayoutResult.toGlyphs()")

  for (offset in 0 until glyphs.length) {
    val box = glyphs.boundingBoxes[offset]
    // translate to the glyph's original position if autoTranslate is requested.
    // This helps to easily implement animations relative to glyph's original position.
    translate(
      left = if (autoTranslate) box.left else 0f,
      top = if (autoTranslate) box.top else 0f
    ) {
      val glyphDrawScope = glyphs.glyphDrawScopes[offset]
      // GlyphDrawScope requires a backing DrawScope to delegate draw operations in [block].
      glyphDrawScope.withDrawScope(this@translate) {
        this@withDrawScope.block()
      }
    }
  }
}

@Composable
fun DemoDropping(text: String) {
    val textMeasurer = rememberTextMeasurer()
    val animatables = remember { List(text.length) { Animatable(0f) } }
    val coroutineScope = rememberCoroutineScope()

    Button(onClick = {
        coroutineScope.launch {
            animatables.forEach { it.snapTo(0f) }
            animatables.indices.toList().shuffled().forEachIndexed { index, animatableIndex ->
                launch {
                    delay(index * kotlin.random.Random.nextLong(75L, 100L))
                    animatables[animatableIndex].snapTo(20f)
                    animatables[animatableIndex].animateTo(1f, tween(500))
                }
            }
        }
    }) {
        Text("Drop")
    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)
        .drawWithCache {
            val textLayoutResult = textMeasurer.measure(
                text = AnnotatedString(text),
                style = TextStyle(
                    fontSize = 40.sp,
                    brush = Brush.horizontalGradient(colors = listOf(Color.Blue, Color.Red))
                ),
                constraints = Constraints.fixed(size.width.roundToInt(), size.height.roundToInt())
            )
            val glyphs = textLayoutResult.toGlyphs()
            onDrawBehind {
                drawGlyphs(glyphs) {
                    val value = animatables[offset].value
                    if (value > 0f) {
                        scale(value) {
                            this@drawGlyphs.drawGlyph(alpha = (20f - value) / 19f)
                        }
                    }
                }
            }
        })
}

@Composable
fun <T: Comparable<T>> SlideInOutLayout(
    targetState: T,
    modifier: Modifier = Modifier,
    animationSpec: FiniteAnimationSpec<Float> = tween(),
    content: @Composable (T) -> Unit
) {
    val items = remember { mutableStateListOf<CrossSlideAnimationItem<T>>() }
    val transitionState = remember { MutableTransitionState(targetState) }
    val targetChanged = (targetState != transitionState.targetState)
    transitionState.targetState = targetState
    val transition = updateTransition(transitionState, label = "")
    if (targetChanged || items.isEmpty()) {
        // Only manipulate the list when the state is changed, or in the first run.
        val keys = items.map { it.key }.run {
            if (!contains(targetState)) {
                toMutableList().also { it.add(targetState) }
            } else {
                this
            }
        }
        items.clear()
        keys.mapTo(items) { key ->
            CrossSlideAnimationItem(key) {
                val visibility = transition.animateFloat(
                    transitionSpec = { animationSpec }, label = ""
                ) {
                    when {
                        it == key ->  0f
                        it > key ->  -1f
                        else -> 1f
                    }
                }
                Box(Modifier.then(PercentageLayoutOffset(rawOffset = visibility))) {
                    content(key)
                }
            }
        }
    } else if (transitionState.currentState == transitionState.targetState) {
        // Remove all the intermediate items from the list once the animation is finished.
        items.removeAll { it.key != transitionState.targetState }
    }

    Box(modifier) {
        items.forEach {
            key(it.key) {
                it.content()
            }
        }
    }
}

data class CrossSlideAnimationItem<T>(
    val key: T,
    val content: @Composable () -> Unit
)

// Taken from https://github.com/zach-klippenstein/compose-backstack
class PercentageLayoutOffset(private val rawOffset: State<Float>) : LayoutModifier {
    private val offset = { rawOffset.value.coerceIn(-1f..1f) }

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            placeable.place(offsetPosition(IntSize(placeable.width, placeable.height)))
        }
    }

    private fun offsetPosition(containerSize: IntSize) = IntOffset(
        // RTL is handled automatically by place.
        x = (containerSize.width * offset()).toInt(),
        y = 0
    )

    override fun toString(): String = "${this::class.java.simpleName}(offset=$offset)"
}
