package com.alerdoci.marvelsuperheroes.app.common.utils

import android.app.Activity
import androidx.annotation.FloatRange
import androidx.compose.animation.*
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Light
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.AccessibilityManager
import androidx.compose.ui.platform.LocalAccessibilityManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors
import com.yagmurerdogan.toasticlib.Toastic
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import smith.lib.alerts.toast.AdaptiveSToast
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import kotlin.coroutines.resume
import kotlin.math.min


val LocalToastHostState =
    compositionLocalOf<ToastHostState> { error("ToastHostState not present") }

@Composable
fun rememberToastHostState() = remember { ToastHostState() }

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ToastHost(
    hostState: ToastHostState = LocalToastHostState.current,
    modifier: Modifier = Modifier.fillMaxSize(),
    alignment: Alignment = Alignment.BottomCenter,
//    isIconAnimated: Boolean? = false,
    toast: @Composable (ToastData) -> Unit = { Toast(it) }
) {
    val currentToastData = hostState.currentToastData
    val accessibilityManager = LocalAccessibilityManager.current
    LaunchedEffect(currentToastData) {
        if (currentToastData != null) {
            val duration = currentToastData.visuals.duration.toMillis(accessibilityManager)
            delay(duration)
            currentToastData.dismiss()
        }
    }

    AnimatedContent(
        targetState = currentToastData,
        transitionSpec = { ToastDefaults.transition }, label = ""
    ) {
        Box(modifier = modifier) {
            Box(modifier = Modifier.align(alignment)) {
                it?.let { toast(it) }
            }
        }
    }

}

@Composable
fun Toast(
    toastData: ToastData,
    modifier: Modifier = Modifier,
    shape: Shape = ToastDefaults.shape,
    containerColor: Color = ToastDefaults.color,
    contentColor: Color = ToastDefaults.contentColor,
//    isIconAnimated: Boolean = false
) {
//    val animatedModifier = if (isIconAnimated) {
//        val scaleAnimation = rememberInfiniteTransition(label = "").animateFloat(
//            initialValue = 1.0f,
//            targetValue = 0.7f,
//            animationSpec = infiniteRepeatable(
//                animation = tween(durationMillis = 1000),
//                repeatMode = RepeatMode.Reverse
//            ), label = ""
//        )
//        Modifier
//            .size(24.dp)
//            .graphicsLayer(
//                scaleX = scaleAnimation.value,
//                scaleY = scaleAnimation.value
//            )
//    } else {
        Modifier.size(24.dp)
//    }

    val configuration = LocalConfiguration.current
    val sizeMin = min(configuration.screenWidthDp, configuration.screenHeightDp).dp
    Card(
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        modifier = if (modifier != Modifier) modifier else
            Modifier
                .heightIn(min = 48.dp)
                .widthIn(min = 0.dp, max = (sizeMin * 0.7f))
                .padding(
                    bottom = sizeMin * 0.2f,
                    top = 24.dp,
                    start = 12.dp,
                    end = 12.dp
                )
                .imePadding()
                .systemBarsPadding()
                .alpha(0.95f),
        shape = shape
    ) {
        Row(
            Modifier
                .border(2.dp, MarvelColors.blue_grey_800, MaterialTheme.shapes.extraLarge)
                .padding(10.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            toastData.visuals.icon?.let { Icon(it, null) }
            Spacer(modifier = Modifier.size(8.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = toastData.visuals.title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(end = 6.dp)
                )
                Text(
                    style = MaterialTheme.typography.bodySmall,
                    text = toastData.visuals.description,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(end = 6.dp)
                )
            }

        }

    }
}

@Composable
fun StartAnimation(modifier: Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    Modifier
        .graphicsLayer(scaleX = scale, scaleY = scale)
        .then(modifier)
}

@Stable
class ToastHostState {

    private val mutex = Mutex()

    var currentToastData by mutableStateOf<ToastData?>(null)
        private set

    @OptIn(ExperimentalMaterial3Api::class)
    suspend fun showToast(
        title: String,
        description: String,
        icon: ImageVector? = null,
        duration: ToastDuration = ToastDuration.Short,
//        isIconAnimated: Boolean?
    ) = showToast(ToastVisualsImpl(title, description, icon, duration,
//        isIconAnimated
    ))


    @ExperimentalMaterial3Api
    suspend fun showToast(visuals: ToastVisuals) = mutex.withLock {
        try {
            suspendCancellableCoroutine { continuation ->
                currentToastData = ToastDataImpl(visuals, continuation)
            }
        } finally {
            currentToastData = null
        }
    }

    private class ToastVisualsImpl(
        override val title: String,
        override val description: String,
        override val icon: ImageVector? = null,
        override val duration: ToastDuration,
//        override val isIconAnimated: Boolean?
    ) : ToastVisuals {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as ToastVisualsImpl

            if (title != other.title) return false
            if (description != other.description) return false
            if (icon != other.icon) return false
            if (duration != other.duration) return false

            return true
        }

        override fun hashCode(): Int {
            var result = title.hashCode()
            result = 31 * result + icon.hashCode()
            result = 31 * result + duration.hashCode()
            return result
        }
    }

    private class ToastDataImpl(
        override val visuals: ToastVisuals,
        private val continuation: CancellableContinuation<Unit>
    ) : ToastData {

        override fun dismiss() {
            if (continuation.isActive) continuation.resume(Unit)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as ToastDataImpl

            if (visuals != other.visuals) return false
            if (continuation != other.continuation) return false

            return true
        }

        override fun hashCode(): Int {
            var result = visuals.hashCode()
            result = 31 * result + continuation.hashCode()
            return result
        }
    }
}

@Stable
interface ToastData {
    val visuals: ToastVisuals
    fun dismiss()
}

@Stable
interface ToastVisuals {
    val title: String
    val description: String
    val icon: ImageVector?
    val duration: ToastDuration
//    val isIconAnimated: Boolean?
}

enum class ToastDuration { Short, Long, ASecond, Infinite }

object ToastDefaults {
    @OptIn(ExperimentalAnimationApi::class)
    val transition: ContentTransform
        get() = fadeIn(tween(300)) + scaleIn(
            tween(500),
            transformOrigin = TransformOrigin(0.5f, 1f)
        ) + slideInVertically(
            tween(500)
        ) { it / 2 } with fadeOut(tween(250)) + slideOutVertically(tween(500)) { it / 2 } + scaleOut(
            tween(750),
            transformOrigin = TransformOrigin(0.5f, 1f)
        )
    val contentColor: Color @Composable get() = MaterialTheme.colorScheme.inverseOnSurface.harmonizeWithPrimary()
    val color: Color @Composable get() = MaterialTheme.colorScheme.inverseSurface.harmonizeWithPrimary()
    val shape: Shape @Composable get() = MaterialTheme.shapes.extraLarge
}

private fun ToastDuration.toMillis(
    accessibilityManager: AccessibilityManager?
): Long {
    val original = when (this) {
        ToastDuration.Long -> 6500L
        ToastDuration.Short -> 3500L
        ToastDuration.ASecond -> 1000L
        ToastDuration.Infinite -> 100000L
    }
    return accessibilityManager?.calculateRecommendedTimeoutMillis(
        original,
        containsIcons = true,
        containsText = true
    ) ?: original
}

private fun Color.blend(
    color: Color,
    @FloatRange(from = 0.0, to = 1.0) fraction: Float = 0.2f
): Color = Color(ColorUtils.blendARGB(this.toArgb(), color.toArgb(), fraction))

@Composable
private fun Color.harmonizeWithPrimary(
    @FloatRange(
        from = 0.0,
        to = 1.0
    ) fraction: Float = 0.2f
): Color = blend(MaterialTheme.colorScheme.primary, fraction)


@Preview
@Composable
fun ToastPreview() {

//Usage example
    val toastHostState = remember { ToastHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    //    val themeMode = ThemeMode.values()[viewModel.getThemeValue()]
//    val iconRes = if (themeMode == ThemeMode.Dark) {
//        Icons.Default.Campaign
//    } else {
//        Icons.Default.Light
//    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MarvelColors.white),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                scope.launch {
                    toastHostState.showToast(
                        "The title",
                        "This is the description",
                        Icons.Filled.Light,
                        ToastDuration.Short,
//                        true
                    )
                }
            }
        ) {
            Text("Show Toast")
        }
        Button(
            onClick = {
                scope.launch {
                    MotionToast.createToast(
                        context as Activity,
                        "Hurray success 😍",
                        "Upload Completed successfully!",
                        MotionToastStyle.SUCCESS,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(context, R.font.roc_grotesk_black)
                    )
                }
            }
        ) {
            Text("Show motionToast")
        }
        Button(
            onClick = {
                AdaptiveSToast.with(context)
                    .title("YAIXXXX")
                    .text("MORE YAIXXX")
                    .icon(R.drawable.ic_eye, R.color.green_A400)
                    .duration(AdaptiveSToast.LENGTH_LONG)
                    .show();
            }
        ) {
            Text("Show AdaptiveSToast")
        }
        Button(
            onClick = {
                Toastic.toastic(
                    context = context,
                    message = "This is Default Toastic!",
                    duration = Toastic.LENGTH_LONG,
                    type = Toastic.ERROR,
                    isIconAnimated = true,
                    customIcon = R.drawable.ic_comic,
                    font = R.font.roc_grotesk_bold,
                    customBackground = R.drawable.gradient,
//                    textColor = Color.Blue.toArgb(),
                    textColor = MarvelColors.red_500.toArgb(),
//                    customIconAnimation = R.anim.pulse_efect
                    customIconAnimation = com.yagmurerdogan.toastic.R.anim.shake
                ).show()
            }
        ) {
            Text("Show toastic")
        }

        ToastHost(hostState = toastHostState)
    }
}
