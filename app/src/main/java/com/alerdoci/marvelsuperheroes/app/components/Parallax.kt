package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alerdoci.marvelsuperheroes.app.common.utils.Constants.LOREM_IPSUM_SHORT
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.red_900

@Composable
fun Parallax() {

    val scrollState = rememberScrollState()

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .graphicsLayer {
                        alpha = 1f - (scrollState.value.toFloat() / scrollState.maxValue)
                        translationY = 0.5f * scrollState.value
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(com.alerdoci.marvelsuperheroes.R.drawable.marvel_superheroes_onboarding),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = "Personaje nombre",
                modifier = Modifier.padding(8.dp),
                style = androidx.compose.material3.MaterialTheme.typography.titleLarge
            )
            Text(
                text = LOREM_IPSUM_SHORT,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .graphicsLayer {
                    alpha = 1f.coerceAtMost((scrollState.value.toFloat() / scrollState.maxValue))
                }
                .background(red_900),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "TITULO TOPBAR",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 12.dp),
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun ParallaxPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Parallax()
    }
}
