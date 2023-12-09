package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alerdoci.marvelsuperheroes.R

enum class BoxState {
    START,
    END
}

@Composable
fun MainScreen() {
    var boxState by remember { mutableStateOf(BoxState.START) }
    val scale: Float by animateFloatAsState(
        targetValue = if (boxState == BoxState.START) 1f else 10f,
        animationSpec = keyframes {
            durationMillis = 1000
            5f.at(400).with(FastOutSlowInEasing)
            delayMillis = 50
        }, label = ""
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyObject(scale = scale)
        Button(
            onClick = {
                boxState = when (boxState) {
                    BoxState.START -> BoxState.END
                    BoxState.END -> BoxState.START
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(text = "Animate")
        }
    }
}

@Composable
fun MyObject(
    modifier: Modifier = Modifier,
    scale: Float
) {
    Image(
        painter = painterResource(id = R.drawable.groot_placeholder),
        contentDescription = null,
        modifier = modifier
            .scale(scale)
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
