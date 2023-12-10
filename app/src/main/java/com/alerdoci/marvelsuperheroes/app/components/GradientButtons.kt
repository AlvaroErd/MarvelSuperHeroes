package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun GradientSurface() {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            // Gap between children = 26 dp
            verticalArrangement = Arrangement.spacedBy(26.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val cornerRadius = 16.dp
            //...........................................................................
            Spacer(modifier = Modifier.height(8.dp))
            val gradientColor = listOf(Color(0xFFff00cc), Color(0xFF333399))



            GradientButton(
                gradientColors = gradientColor,
                cornerRadius = cornerRadius,
                nameButton = "Style: top Start",
                roundedCornerShape = RoundedCornerShape(topStart = 30.dp)
            )
            GradientButton(
                gradientColors = gradientColor,
                cornerRadius = cornerRadius,
                nameButton = "Style: top End",
                roundedCornerShape = RoundedCornerShape(topEnd = 30.dp)
            )

            GradientButton(
                gradientColors = gradientColor,
                cornerRadius = cornerRadius,
                nameButton = "Style: Bottom Start",
                roundedCornerShape = RoundedCornerShape(bottomStart = 30.dp)
            )
            GradientButton(
                gradientColors = gradientColor,
                cornerRadius = cornerRadius,
                nameButton = "Style: Bottom End",
                roundedCornerShape = RoundedCornerShape(bottomEnd = 30.dp)
            )



            GradientButton(
                gradientColors = gradientColor,
                cornerRadius = cornerRadius,
                nameButton = "Style: Top Start & Bottom End",
                roundedCornerShape = RoundedCornerShape(topStart = 30.dp, bottomEnd = 30.dp)
            )
            GradientButton(
                gradientColors = gradientColor,
                cornerRadius = cornerRadius,
                nameButton = "Style: Top End & Bottom Start",
                roundedCornerShape = RoundedCornerShape(topEnd = 30.dp, bottomStart = 30.dp)
            )


            GradientButton(
                gradientColors = gradientColor,
                cornerRadius = cornerRadius,
                nameButton = "Style: All side",
                roundedCornerShape = RoundedCornerShape(30.dp)
            )


            //.............................................
            GradientButtonDisable(
                gradientColors = gradientColor,
                cornerRadius = cornerRadius
            )

            GradientButtonNoRipple(
                gradientColors = gradientColor,
                cornerRadius = cornerRadius
            )

        }


    }
}


//...........................................................................
@Composable
private fun GradientButton(
    gradientColors: List<Color>,
    cornerRadius: Dp,
    nameButton: String,
    roundedCornerShape: RoundedCornerShape
) {

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        onClick = {
            //your code
        },

        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(cornerRadius)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(colors = gradientColors),
                    shape = roundedCornerShape
                )
                .clip(roundedCornerShape)
                /*.background(
                    brush = Brush.linearGradient(colors = gradientColors),
                    shape = RoundedCornerShape(cornerRadius)
                )*/
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = nameButton,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}

@Composable
private fun GradientButtonDisable(
    gradientColors: List<Color>,
    cornerRadius: Dp,
    disabledColors: List<Color> = listOf(Color.Transparent, Color.Transparent)
) {

    var enabled by remember { mutableStateOf(true) }

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        onClick = {
            enabled = false
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(cornerRadius),
        enabled = enabled,
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(colors = if (enabled) gradientColors else disabledColors),
                    shape = RoundedCornerShape(cornerRadius)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (enabled) "Disable Me" else "I'm Disabled!",
                fontSize = 20.sp,
                color = if (enabled) Color.White else Color.Black.copy(alpha = 0.6F)
            )
        }
    }
}

@Composable
private fun GradientButtonNoRipple(
    gradientColors: List<Color>,
    cornerRadius: Dp
) {

    // This is used to disable the ripple effect
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp)
            .background(
                brush = Brush.linearGradient(colors = gradientColors),
                shape = RoundedCornerShape(cornerRadius)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(
                indication = null, // Assign null to disable the ripple effect
                interactionSource = interactionSource
            ) {
                // your code
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No Ripple",
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }

}