package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alerdoci.marvelsuperheroes.R

@Composable
fun LottieHeader(modifier: Modifier) {

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.lottie_spaceman_coding))

    LottieAnimation(
        composition = composition,
        isPlaying = true,
        restartOnPlay = true,
        iterations = LottieConstants.IterateForever,
        modifier = modifier
    )

}

@Preview
@Composable
fun LottieHeaderPreview() {

    LottieHeader(
        modifier = Modifier
            .size(200.dp)
    )

}











