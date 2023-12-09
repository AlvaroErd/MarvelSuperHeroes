package com.alerdoci.marvelsuperheroes.app.components


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLink
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.common.extensions.ModifierExtensions
import com.alerdoci.marvelsuperheroes.app.common.extensions.ModifierExtensions.rememberShakerState
import com.alerdoci.marvelsuperheroes.app.common.extensions.ModifierExtensions.shaker
import com.alerdoci.marvelsuperheroes.app.theme.MarvelSuperHeroesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShakerScreen(
    modifier: Modifier,
    onClose: () -> Unit
) {
    var color by remember { mutableStateOf(Color.Red) }
    val animatedColor by animateColorAsState(targetValue = color, label = "color")
    val shakerController = rememberShakerState()
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier.graphicsLayer { this.alpha = alpha },
                title = { Text("Shaker") },
                navigationIcon = { CloseIcon (onClick = onClose) },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Shake me",
                modifier = Modifier
                    .shaker(shakerController)
                    .background(animatedColor.copy(alpha = .2f))
                    .border(2.dp, animatedColor)
                    .padding(8.dp)
            )
            Button(onClick = {
                color = Color.Red
                shakerController.shake(
                    ModifierExtensions.ShakeConfig(
                        iterations = 4,
                        translateX = 10f,
                        rotateY = 10f,
                        intensity = 2_000f
                    )
                )
            }) {
                Text("Fail!")
            }
            Button(onClick = {
                color = Color.Green
                shakerController.shake(
                    ModifierExtensions.ShakeConfig(
                        iterations = 4,
                        rotateX = -20f,
                        translateY = 20f,
                    )
                )
            }) {
                Text("Success!")
            }
        }
    }
}

@Preview
@Composable
private fun ShakerScreenPreview() {
        ShakerScreen(
            modifier = Modifier.fillMaxSize(),
            onClose = {}
        )
}
