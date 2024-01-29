package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class LoadingIndicatorTypes {
    Pulsing,
    Flashing,
    Bouncing
}

/**
 * Renders a set of dots that are animated to indicate a "loading" state.
 *
 * @param loadingIndicatorType A [LoadingIndicatorTypes] that sets the type of indicator
 *   to use for this button.
 *   Defaults to [LoadingIndicatorTypes.Pulsing]
 * @param dotSize How big to render the dots.
 *   Defaults to 12.dp
 * @param color A Color to use for the dots. If not specified, the LocalContentColor will be used.
 */
@Composable
fun LoadingIndicator(type: LoadingIndicatorTypes = LoadingIndicatorTypes.Pulsing, dotSize: Dp = 12.dp, modifier: Modifier = Modifier, color: Color = Color.Unspecified) {
    val numberOfDots = 3
    val pulseDuration = 333
    val bouncingDotHeight = 10f

    val timeBetween = pulseDuration * (numberOfDots - 1)
    val delay = pulseDuration / 2
    val transition = rememberInfiniteTransition()

    val minWidth = (dotSize * numberOfDots) + ((dotSize / 2) * (numberOfDots - 1))
    var rowModifier = modifier.widthIn(min = minWidth)
    if (type == LoadingIndicatorTypes.Bouncing) rowModifier = rowModifier.padding(top = bouncingDotHeight.dp)

    val dotColor = color.takeOrElse { LocalContentColor.current }

    Row(modifier = rowModifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        for (index in 0 until numberOfDots) {
            when (type) {
                LoadingIndicatorTypes.Pulsing -> PulsingDot(
                    dotSize = dotSize,
                    pulseDuration = pulseDuration,
                    timeBetweenPulses = timeBetween,
                    delayStart = delay * index,
                    transition = transition,
                    color = dotColor)

                LoadingIndicatorTypes.Flashing -> FlashingDot(
                    dotSize = dotSize,
                    flashDuration = pulseDuration,
                    timeBetweenFlashes = timeBetween,
                    delayStart = delay * index,
                    transition = transition,
                    color = dotColor)

                LoadingIndicatorTypes.Bouncing -> BouncingDot(
                    dotSize = dotSize,
                    bounceHeight = bouncingDotHeight,
                    bounceDuration = pulseDuration,
                    timeBetweenBounces = timeBetween,
                    delayStart = delay * index,
                    transition = transition,
                    color = dotColor)
            }
        }
    }
}


/**
 * Renders a "dot" (circle) shape with a specific color.
 *
 * @param color The Color for the dot. Defaults to the current value of LocalContentColor
 */
@Composable
fun Dot(modifier: Modifier = Modifier, color: Color = LocalContentColor.current) {
    Spacer(modifier.background(color = color, shape = CircleShape))
}

/**
 * Takes in a set of values and a duration, and returns a
 * [State]<Float> that changes the value over the course of the duration,
 * and then repeats indefinitely. The value will be moved from the min
 * to the max, and then back to the min over the course of the duration.
 *
 * This can be used to create an infinite repeatable "loop" that animates
 * a value over and over, and include a "pause" between cycles.
 *
 * @param minValue the starting value at the beginning (and end of the duration).
 * @param maxValue the value that should be output halfway through the duration.
 * @param duration how long (in milliseconds) the transistion from min to max and back to min should take.
 * @param timeBetween how long (in milliseconds) to "wait" between cycles of the duration.
 *   The total time of the repeated loop is `duration + timeBetween`
 * @param delayStart how long (in milliseconds) to wait before starting the initial transition.
 */
@Composable
private fun InfiniteTransition.dotAnimator(
    minValue: Float = 0f,
    maxValue: Float =  1f,
    duration: Int = 333,
    timeBetween: Int = 0,
    delayStart: Int = 0): State<Float> {

    val totalDuration = duration + timeBetween

    return animateFloat (
        initialValue = minValue,
        targetValue = minValue,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = totalDuration
                minValue at 0 with LinearOutSlowInEasing
                maxValue at (duration / 2) with FastOutLinearInEasing
                minValue at duration
            },
            initialStartOffset = StartOffset(delayStart)
        ), label = ""
    )
}

/**
 * Renders a [Dot] that "pulses". The dot will grow and shrink
 * as well as fade in and out in a loop.
 *
 * @param dotSize The size of the dot to be rendered.
 *   Defaults to 12.dp
 * @param color The Color of the dot.
 *   Defaults to the current value of LocalContentColor.
 * @param pulseDuration how long one "pulse" of the dot should take.
 *   Defaults to 300 milliseconds.
 * @param timeBetweenPulses how long to "pause" between pulses.
 *   Defaults to 0 milliseconds (no pause).
 * @param delayStart how long to "wait" before the initial pulse.
 *   The dot will not be displayed during this time.
 *   Defaults to 0 milliseconds (no delay).
 * @param transition an [InfiniteTransition] to use for animating the dot.
 *   This is useful if you want to combine this animation with others
 */
@Composable
fun PulsingDot(
    dotSize: Dp = 12.dp,
    color: Color = LocalContentColor.current,
    pulseDuration: Int = 300,
    timeBetweenPulses: Int = 0,
    delayStart: Int = 0,
    transition: InfiniteTransition = rememberInfiniteTransition()) {

    val scale: Float by transition.dotAnimator(
        duration = pulseDuration,
        timeBetween = timeBetweenPulses,
        delayStart = delayStart)

    val alpha: Float by transition.dotAnimator(
        minValue = 0.25f,
        maxValue = 1f,
        duration = pulseDuration,
        timeBetween = timeBetweenPulses,
        delayStart = delayStart)

    Dot(modifier = Modifier
        .size(dotSize)
        .scale(scale), color = color.copy(alpha = alpha))
}

/**
 * Renders a [Dot] that "flashes". The dot will fade in and out in a loop.
 * The dot will always be visible somewhat (with very low alpha) even when fully
 * faded out.
 *
 * @param dotSize The size of the dot to be rendered.
 *   Defaults to 12.dp
 * @param color The Color of the dot.
 *   Defaults to the current value of LocalContentColor.
 * @param flashDuration how long one "flash" of the dot should take.
 *   Defaults to 300 milliseconds.
 * @param timeBetweenFlashes how long to "pause" between flashes.
 *   Defaults to 0 milliseconds (no pause).
 * @param delayStart how long to "wait" before the initial flash.
 *   Defaults to 0 milliseconds (no delay).
 * @param transition an [InfiniteTransition] to use for animating the dot.
 *   This is useful if you want to combine this animation with others
 */
@Composable
fun FlashingDot(
    dotSize: Dp = 12.dp,
    color: Color = LocalContentColor.current,
    flashDuration: Int = 300,
    timeBetweenFlashes: Int = 0,
    delayStart: Int = 0,
    transition: InfiniteTransition = rememberInfiniteTransition()) {

    val alpha: Float by transition.dotAnimator(
        minValue = 0.25f, // You could set this to 0 to fully fade out the dot
        maxValue = 1f,
        duration = flashDuration,
        timeBetween = timeBetweenFlashes,
        delayStart = delayStart)

    Dot(modifier = Modifier.size(dotSize), color = color.copy(alpha = alpha))
}

/**
 * Renders a [Dot] that "bounces" up and down.
 *
 * @param dotSize The size of the dot to be rendered.
 *   Defaults to 12.dp
 * @param color The Color of the dot.
 *   Defaults to the current value of LocalContentColor.
 * @param bounceHeight - How high (in pixels) the dot should "bounce" (move upward).
 *   Defaults to 10.
 * @param bounceDuration how long one "bounce" of the dot should take.
 *   Defaults to 300 milliseconds.
 * @param timeBetweenBounces how long to "pause" between bounces.
 *   Defaults to 0 milliseconds (no pause).
 * @param delayStart how long to "wait" before the initial bounce.
 *   Defaults to 0 milliseconds (no delay).
 * @param transition an [InfiniteTransition] to use for animating the dot.
 *   This is useful if you want to combine this animation with others
 */
@Composable
fun BouncingDot(
    dotSize: Dp = 12.dp,
    color: Color = LocalContentColor.current,
    bounceHeight: Float = 10f,
    bounceDuration: Int = 300,
    timeBetweenBounces: Int = 0,
    delayStart: Int = 0,
    transition: InfiniteTransition = rememberInfiniteTransition()) {

    val offset: Float by transition.dotAnimator(
        minValue = 0f,
        maxValue = bounceHeight,
        duration = bounceDuration,
        timeBetween = timeBetweenBounces,
        delayStart = delayStart)

    Dot(modifier = Modifier
        .size(dotSize)
        .offset(y = -offset.dp), color = color)
}


@Preview()
@Composable
fun PulsingPreview() {
        LoadingIndicator()
}

@Preview
@Composable
fun FlashingPreview() {
        LoadingIndicator(type = LoadingIndicatorTypes.Flashing)
}

@Preview
@Composable
fun BouncingPreview() {
        LoadingIndicator(type = LoadingIndicatorTypes.Bouncing)
}



/**
 * Sets a shape to use for all My Buttons.
 */
private val buttonShape = RoundedCornerShape(50)


/**
 * Alpha to use for disabled buttons and disabled content
 */
private const val DISABLED_BUTTON_ALPHA = 0.5f


/**
 * Sets the content padding to use on all buttons
 */
private val buttonContentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp)


/**
 * Renders a solid, filled in Button.
 * The button will have a solid background color with text on top.
 *
 * @param text The text to show on the button.
 * @param onClick A callback that is invoked when the button is clicked.
 * @param enabled Whether the button can be clicked or not.
 *   When NOT enabled, the onClick() handler will NOT be called when the button is clicked.
 *   When NOT enabled, the button will use the "disabled" colors in the passed in ButtonColors.
 *   This value will be ignored (and set to false) if the loading argument is true.
 *   Defaults to true.
 * @param loading A boolean indicating if the button is in the loading state. If this is
 *   set to true, then enabled will automatically be set to false.
 *   Defaults to false.
 * @param loadingIndicatorType A [LoadingIndicatorTypes] that sets the type of indicator
 *   to use for this button.
 *   Defaults to [LoadingIndicatorTypes.Pulsing]
 */
@Composable
fun MyButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: ()->Unit = {},
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingIndicatorType: LoadingIndicatorTypes = LoadingIndicatorTypes.Pulsing,
) {

    // The default material button colors use a shade of gray
    // for the disabled state. These buttons instead use
    // an alpha on the primary color.
    // If you want to use a different overall button color, just
    // change the `buttonColor` variable below. Or, for even more flexibility,
    // allow the caller to pass in a `buttonColor`.

    val buttonColor = androidx.compose.material3.MaterialTheme.colorScheme.primary
    val textColor = contentColorFor(backgroundColor = buttonColor)

    val colors = ButtonDefaults.buttonColors(
        containerColor = buttonColor,
        contentColor = textColor,
        disabledContainerColor = buttonColor.copy(alpha = DISABLED_BUTTON_ALPHA),
        disabledContentColor =  textColor.copy(alpha = DISABLED_BUTTON_ALPHA)
    )

    Button(
        modifier = modifier,
        colors = colors,
        shape = buttonShape,
        contentPadding = buttonContentPadding,
        enabled = enabled && !loading,
        onClick = onClick) {

        MyButtonContent(
            text = text,
            loading = loading,
            loadingIndicatorType = loadingIndicatorType)
    }
}


/**
 * Renders a outlined Button.
 * The button will have a transparent background with
 * a colored border and text in the center.
 *
 * @param text The text to show on the button.
 * @param onClick A callback that is invoked when the button is clicked.
 * @param enabled Whether the button can be clicked or not.
 *   When NOT enabled, the onClick() handler will NOT be called when the button is clicked.
 *   When NOT enabled, the button will use the "disabled" colors in the passed in ButtonColors.
 *   This value will be ignored (and set to false) if the loading argument is true.
 *   Defaults to true.
 * @param loading A boolean indicating if the button is in the loading state. If this is
 *   set to true, then enabled will automatically be set to false.
 *   Defaults to false.
 * @param loadingIndicatorType A [LoadingIndicatorTypes] that sets the type of indicator
 *   to use for this button.
 *   Defaults to [LoadingIndicatorTypes.Pulsing]
 */
@Composable
fun MyOutlinedButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: ()->Unit = {},
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingIndicatorType: LoadingIndicatorTypes = LoadingIndicatorTypes.Pulsing,
) {
    val colors = ButtonDefaults.outlinedButtonColors()

    // Set the border color using the button content color

    OutlinedButton(
        enabled = enabled,
        contentPadding = buttonContentPadding,
        shape = buttonShape,
//        border = BorderStroke(2.dp, borderColor),
        modifier = modifier,
        colors = colors,
        onClick = onClick) {

        MyButtonContent(
            text = text,
            loading = loading,
            loadingIndicatorType = loadingIndicatorType)
    }
}

/**
 * Renders the content in a Solid or Outlined button.
 *
 * @param text The text to show on the button.
 * @param loading Whether to show the loading indicator.
 *   If true, the text will still be rendered, but will be transparent/invisible.
 * @param loadingIndicatorType A [LoadingIndicatorTypes] that sets the type of indicator
 *   to use for this button.
 */
@Composable
private fun MyButtonContent(
    text: String,
    loading: Boolean,
    loadingIndicatorType: LoadingIndicatorTypes) {

    // Use a Custom Layout so that we can measure the width of both the
    // button text and the indicator and make sure that the resulting
    // layout is sized to fit either/both.
    // Then we can place either the text or the indicator based on the loading state.
    // This helps ensure that the button does not change size when switching the loading state.
    Layout(
        content = {
            // Content is the Text and the LoadingIndicator
            Text(text = text, modifier = Modifier.layoutId("buttonText"))
            LoadingIndicator(type = loadingIndicatorType, modifier = Modifier.layoutId("loadingIndicator"))
        }) { measureables, constraints ->

        // First, measure both the text and the indicator, with no additional contraints on their size.
        val textPlaceable = measureables.first { it.layoutId == "buttonText"}.measure(constraints)
        val indicatorPlaceable = measureables.first { it.layoutId == "loadingIndicator"}.measure(constraints)

        // Now calculate the layout width,
        // making sure it's big enough to fit the larger of the 2 placeables.
        val layoutWidth = textPlaceable.width.coerceAtLeast(indicatorPlaceable.width)
        val layoutHeight = textPlaceable.height.coerceAtLeast(indicatorPlaceable.height)

        // Now, create the layout at the appropriate size
        layout(layoutWidth,layoutHeight) {
            // Place EITHER the indicator or the text (but not both), based on the loading state
            if (loading) {
                // Calculate the X and Y position of the indicator so that it's centered in the layout.
                val indicatorX = (layoutWidth - indicatorPlaceable.width) / 2
                val indicatorY = (layoutHeight - indicatorPlaceable.height) / 2
                // Place the indicator
                indicatorPlaceable.placeRelative(x = indicatorX, y = indicatorY)
            } else {
                // Calculate the X and Y position of the text so that it's centered in the layout.
                val textX = (layoutWidth - textPlaceable.width) / 2
                val textY = (layoutHeight - textPlaceable.height) / 2
                //Place the text
                textPlaceable.placeRelative(x = textX, y = textY)
            }
        }
    }
}

@Composable
fun ButtonsDemo() {
    Column(modifier = Modifier.fillMaxWidth()) {
        ButtonRow(type = LoadingIndicatorTypes.Pulsing)

        Spacer(modifier = Modifier.height(36.dp))

        ButtonRow(type = LoadingIndicatorTypes.Flashing)

        Spacer(modifier = Modifier.height(36.dp))

        ButtonRow(type = LoadingIndicatorTypes.Bouncing)
    }
}

@Composable
fun ButtonRow(type: LoadingIndicatorTypes) {

    // This is a very basic click handler setup for demo purposes
    // to show the loading dots for 5 seconds.
    // A real app would likely include a ViewModel
    // that emits a [State]<Boolean> that can be observed
    // to set the `loading` state of the button.
    var buttonLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val onButtonClicked = {
        scope.launch {
            buttonLoading = true
            delay(5000)
            buttonLoading = false
        }
    }

    Text(type.name, modifier = Modifier.padding(start = 16.dp), fontWeight = FontWeight.Bold, fontSize = 18.sp)
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .padding(vertical = 12.dp)
            .fillMaxWidth()
    ) {
        MyButton(
            text = "Submit",
            loading = buttonLoading,
            loadingIndicatorType = type,
            onClick = { onButtonClicked() })

        MyOutlinedButton(
            text = "Submit",
            loading = buttonLoading,
            loadingIndicatorType = type,
            onClick = { onButtonClicked() })
    }
}

@Preview
@Composable
fun PreviewButtons() {
        ButtonsDemo()
}
