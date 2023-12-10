package com.alerdoci.marvelsuperheroes.app.common.anim

import androidx.compose.runtime.Composable

/**
 * Author : Benjamin Monjoie
 * Credit : https://gist.github.com/bmonjoie/8506040b2ea534eac931378348622725
 */
internal data class CircularRevealAnimationItem<T>(
    val key: T,
    val content: @Composable () -> Unit
)
