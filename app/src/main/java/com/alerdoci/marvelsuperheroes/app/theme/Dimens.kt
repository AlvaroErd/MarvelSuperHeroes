package com.alerdoci.marvelsuperheroes.app.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class Dimens(

    // Text Dimens
    val textHero: TextUnit = 32.sp,
    val headerTextSize: TextUnit = 26.sp,
    val textHeadline: TextUnit = 24.sp,
    val textTitle: TextUnit = 20.sp,
    val textLargeBody: TextUnit = 18.sp,
    val textBody: TextUnit = 16.sp,
    val textSmallBody: TextUnit = 14.sp,
    val textSmallerBody: TextUnit = 12.sp,
    val textTinyBody: TextUnit = 10.sp,
    val textTiny: TextUnit = 6.sp,

    // Custom Dimens
    val custom500: Dp = 500.dp,
    val custom400: Dp = 400.dp,
    val custom300: Dp = 300.dp,
    val custom250: Dp = 250.dp,
    val custom200: Dp = 200.dp,
    val custom195: Dp = 195.dp,
    val custom175: Dp = 175.dp,
    val custom150: Dp = 150.dp,
    val custom140: Dp = 140.dp,
    val custom125: Dp = 125.dp,
    val custom120: Dp = 120.dp,
    val custom110: Dp = 110.dp,
    val custom100: Dp = 100.dp,
    val custom95: Dp = 95.dp,
    val custom90: Dp = 90.dp,
    val custom80: Dp = 80.dp,
    val custom70: Dp = 70.dp,
    val custom64: Dp = 64.dp,
    val custom60: Dp = 60.dp,
    val custom50: Dp = 50.dp,
    val custom45: Dp = 45.dp,
    val custom40: Dp = 40.dp,
    val custom36: Dp = 36.dp,
    val custom32: Dp = 32.dp,
    val custom30: Dp = 30.dp,
    val custom28: Dp = 28.dp,
    val custom24: Dp = 24.dp,
    val custom22: Dp = 22.dp,
    val custom20: Dp = 20.dp,
    val custom18: Dp = 18.dp,
    val custom16: Dp = 16.dp,
    val custom14: Dp = 14.dp,
    val custom10: Dp = 10.dp,
    val custom8: Dp = 8.dp,
    val custom6: Dp = 6.dp,
    val custom5: Dp = 5.dp,
    val custom4: Dp = 4.dp,
    val custom3: Dp = 3.dp,
    val custom2: Dp = 2.dp,
    val custom1: Dp = 1.dp,
    val custom0: Dp = 0.dp,
)

val LocalDimens = compositionLocalOf { Dimens() }

val MaterialTheme.dimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current