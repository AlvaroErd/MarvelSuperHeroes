package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alerdoci.marvelsuperheroes.R

@ExperimentalFoundationApi
@Composable
fun CombinedOnClick() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(300.dp)
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true, color = Color.Red),
                    onClick = {
                        println("onClick")
                    },
                    onDoubleClick = {
                        println("onDoubleClick")
                    },
                    onLongClick = {
                        println("onLongClick")
                    }
                ),
            painter = painterResource(id = R.drawable.marvel_superheroes_onboarding),
            contentDescription = null
        )
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun CombinedOnClickPreview() {
    CombinedOnClick()
}
