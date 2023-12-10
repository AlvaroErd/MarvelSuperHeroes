package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LampPassword() {
    val lightOn = remember {
        mutableStateOf(false)
    }
    AnimatedVisibility(
        visible = lightOn.value,
        enter = fadeIn(animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy)),
        exit = fadeOut(animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy))

    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height / 2.4f
            val path = Path()
            val gradient = Brush.linearGradient(
                start = Offset(size.width / 2, 0f),
                end = Offset(size.width / 2, canvasHeight),
                colors = listOf(
                    Color.White.copy(alpha = .7f),
                    Color.White.copy(alpha = .4f),
                    Color.White.copy(alpha = .1f),
                    Color.Transparent,
                )
            )
            path.moveTo(x = canvasWidth / 2 - 100, y = 50f)
            path.lineTo(x = 0f, y = canvasHeight)
            path.lineTo(x = canvasWidth, y = canvasHeight)
            path.lineTo(x = canvasWidth / 2 + 100, y = 50f)
            path.close()
            this.drawPath(path = path, brush = gradient)
        }
    }
    LightSwitch {
        lightOn.value = !lightOn.value
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        InputField(label = "Username")
        Spacer(modifier = Modifier.height(20.dp))
        InputField(label = "Password", obscureText = !lightOn.value)
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Sign Up",
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputField(label: String, obscureText: Boolean = false) {
    val input = remember {
        mutableStateOf("")
    }
    TextField(
        value = input.value,
        placeholder = {
            Text(
                text = label,
            )
        },
        onValueChange = {
            input.value = it
        },
        shape = RoundedCornerShape(50),
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (obscureText) PasswordVisualTransformation() else VisualTransformation.None,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

@Composable
private fun LightSwitch(onDragEnd: () -> Unit = {}) {
    val height = remember {
        mutableStateOf(40)
    }
    val switchHeight by animateIntAsState(
        targetValue = height.value,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy), label = "",
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            modifier = Modifier
                .height(20.dp)
                .width(100.dp)
                .clip(
                    shape = RoundedCornerShape(50).copy(
                        topStart = CornerSize(0.dp),
                        topEnd = CornerSize(0.dp),
                    ),
                ),
            color = Color.Black,
        ) {

        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(switchHeight.dp)
                    .background(color = Color.White)
            )
            Box(
                modifier = Modifier
                    .size(15.dp)
                    .clip(shape = CircleShape)
                    .background(color = Color.White)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDrag = { _, offset ->
                                if (height.value < 60) {
                                    height.value += offset.y.toInt()
                                }
                            },
                            onDragEnd = {
                                height.value = 40
                                onDragEnd.invoke()
                            }
                        )
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LampPasswordPreview() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.DarkGray
        ) {
            LampPassword()
        }
    }
