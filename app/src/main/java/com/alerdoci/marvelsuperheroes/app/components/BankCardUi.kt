package com.alerdoci.marvelsuperheroes.app.components

// All the needed imports
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alerdoci.marvelsuperheroes.app.theme.RocGrotesk

// Our star of the show: BankCardUi
@Composable
fun BankCardUi(
    // Designing Flexible Composables
    modifier: Modifier = Modifier, // Modifier as Parameter
    baseColor: Color = Color(0xFF1252C8),
    cardNumber: String = "",
    cardHolder: String = "",
    expires: String = "",
    cvv: String = "",
    brand: String = ""
) {
    // Bank Card Aspect Ratio
    val bankCardAspectRatio = 1.586f // (e.g., width:height = 85.60mm:53.98mm)
    Card(
        modifier = modifier
            .fillMaxWidth()
            // Aspect Ratio in Compose
            .aspectRatio(bankCardAspectRatio),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
    ) {
        Box {
            BankCardBackground(baseColor = baseColor)
            BankCardNumber(cardNumber = cardNumber)
            // Positioned to corner top left
            SpaceWrapper(
                modifier = Modifier.align(Alignment.TopStart),
                space = 32.dp,
                top = true,
                left = true
            ) {
                BankCardLabelAndText(label = "card holder", text = cardHolder)
            }
            // Positioned to corner bottom left
            SpaceWrapper(
                modifier = Modifier.align(Alignment.BottomStart),
                space = 32.dp,
                bottom = true,
                left = true
            ) {
                Row {
                    BankCardLabelAndText(label = "expires", text = expires)
                    Spacer(modifier = Modifier.width(16.dp))
                    BankCardLabelAndText(label = "cvv", text = cvv)
                }
            }
            // Positioned to corner bottom right
            SpaceWrapper(
                modifier = Modifier.align(Alignment.BottomEnd),
                space = 32.dp,
                bottom = true,
                right = true
            ) {
                // Feel free to use an image instead
                Text(
                    text = brand, style = TextStyle(
                        fontFamily = RocGrotesk,
                        fontWeight = FontWeight.W500,
                        fontStyle = FontStyle.Italic,
                        fontSize = 22.sp,
                        letterSpacing = 1.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}

// A splash of color for the background
@Composable
fun BankCardBackground(baseColor: Color) {
    val colorSaturation75 = baseColor.setSaturation(0.75f)
    val colorSaturation50 = baseColor.setSaturation(0.5f)
    // Drawing Shapes with Canvas
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(baseColor)
    ) {
        // Drawing Circles
        drawCircle(
            color = colorSaturation75,
            center = Offset(x = size.width * 0.2f, y = size.height * 0.6f),
            radius = size.minDimension * 0.85f
        )
        drawCircle(
            color = colorSaturation50,
            center = Offset(x = size.width * 0.1f, y = size.height * 0.3f),
            radius = size.minDimension * 0.75f
        )
    }
}

@Composable
fun BankCardNumber(cardNumber: String) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween, // Space out the children evenly
        verticalAlignment = Alignment.CenterVertically // Center the children vertically
    ) {
        // Draw the first three groups of dots
        repeat(3) {
            BankCardDotGroup()
        }

        // Display the last four digits
        Text(
            text = cardNumber.takeLast(4),
            style = TextStyle(
                fontFamily = RocGrotesk,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                letterSpacing = 1.sp,
                color = Color.White
            )
        )
    }
}

@Composable
fun BankCardDotGroup() {
    Canvas(
        modifier = Modifier.width(48.dp),
        onDraw = { // You can adjust the width as needed
            val dotRadius = 4.dp.toPx()
            val spaceBetweenDots = 8.dp.toPx()
            for (i in 0 until 4) { // Draw four dots
                drawCircle(
                    color = Color.White,
                    radius = dotRadius,
                    center = Offset(
                        x = i * (dotRadius * 2 + spaceBetweenDots) + dotRadius,
                        y = center.y
                    )
                )
            }
        })
}

@Composable
fun BankCardLabelAndText(label: String, text: String) {
    Column(
        modifier = Modifier
            .wrapContentSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label.uppercase(),
            style = TextStyle(
                fontFamily = RocGrotesk,
                fontWeight = FontWeight.W300,
                fontSize = 12.sp,
                letterSpacing = 1.sp,
                color = Color.White
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            style = TextStyle(
                fontFamily = RocGrotesk,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                letterSpacing = 1.sp,
                color = Color.White
            )
        )
    }
}

@Composable
fun SpaceWrapper(
    modifier: Modifier = Modifier,
    space: Dp,
    top: Boolean = false,
    right: Boolean = false,
    bottom: Boolean = false,
    left: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .then(if (top) Modifier.padding(top = space) else Modifier)
            .then(if (right) Modifier.padding(end = space) else Modifier)
            .then(if (bottom) Modifier.padding(bottom = space) else Modifier)
            .then(if (left) Modifier.padding(start = space) else Modifier)
    ) {
        content()
    }
}

// Take a sneak peek with @Preview
@Composable
@Preview
fun BankCardUiPreview() {
    Box(Modifier.fillMaxSize().padding(16.dp)) {
        BankCardUi(
            modifier = Modifier.align(Alignment.Center),
            baseColor = Color(0xFFFF9800),
            cardNumber = "1234567890123456",
            cardHolder = "Churumbel Calata",
            expires = "01/29",
            cvv = "901",
            brand = "Openbank"
        )
    }
}

fun Color.toHsl(): FloatArray {
    val redComponent = red
    val greenComponent = green
    val blueComponent = blue

    val maxComponent = maxOf(redComponent, greenComponent, blueComponent)
    val minComponent = minOf(redComponent, greenComponent, blueComponent)
    val delta = maxComponent - minComponent
    val lightness = (maxComponent + minComponent) / 2

    val hue: Float
    val saturation: Float

    if (maxComponent == minComponent) {
        // Grayscale color, no saturation and hue is undefined
        hue = 0f
        saturation = 0f
    } else {
        // Calculating saturation
        saturation = if (lightness > 0.5) delta / (2 - maxComponent - minComponent) else delta / (maxComponent + minComponent)
        // Calculating hue
        hue = when (maxComponent) {
            redComponent -> 60 * ((greenComponent - blueComponent) / delta % 6)
            greenComponent -> 60 * ((blueComponent - redComponent) / delta + 2)
            else -> 60 * ((redComponent - greenComponent) / delta + 4)
        }
    }

    // Returning HSL values, ensuring hue is within 0-360 range
    return floatArrayOf(hue.coerceIn(0f, 360f), saturation, lightness)
}

fun hslToColor(hue: Float, saturation: Float, lightness: Float): Color {
    val chroma = (1 - kotlin.math.abs(2 * lightness - 1)) * saturation
    val secondaryColorComponent = chroma * (1 - kotlin.math.abs((hue / 60) % 2 - 1))
    val matchValue = lightness - chroma / 2

    var red = matchValue
    var green = matchValue
    var blue = matchValue

    when ((hue.toInt() / 60) % 6) {
        0 -> { red += chroma; green += secondaryColorComponent }
        1 -> { red += secondaryColorComponent; green += chroma }
        2 -> { green += chroma; blue += secondaryColorComponent }
        3 -> { green += secondaryColorComponent; blue += chroma }
        4 -> { red += secondaryColorComponent; blue += chroma }
        5 -> { red += chroma; blue += secondaryColorComponent }
    }

    // Creating a color from RGB components
    return Color(red = red, green = green, blue = blue)
}

fun Color.setSaturation(newSaturation: Float): Color {
    val hslValues = this.toHsl()
    // Adjusting the saturation while keeping hue and lightness the same
    return hslToColor(hslValues[0], newSaturation.coerceIn(0f, 1f), hslValues[2])
}

