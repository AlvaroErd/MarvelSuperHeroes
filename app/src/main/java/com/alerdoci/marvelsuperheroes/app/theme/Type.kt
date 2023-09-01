package com.alerdoci.marvelsuperheroes.app.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.alerdoci.marvelsuperheroes.R

val RocGrotesk = FontFamily(
    Font(R.font.roc_grotesk_regular, FontWeight.Normal),
    Font(R.font.roc_grotesk_medium, FontWeight.Medium),
    Font(R.font.roc_grotesk_bold, FontWeight.Bold),
    Font(R.font.roc_grotesk_thin, FontWeight.Thin),
    Font(R.font.roc_grotesk_light, FontWeight.Light)
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = RocGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp
    ),
    displayMedium = TextStyle(
        fontFamily = RocGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp
    ),
    displaySmall = TextStyle(
        fontFamily = RocGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = RocGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = RocGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = RocGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = RocGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    bodySmall = TextStyle(
        fontFamily = RocGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 8.sp
    ),
    titleMedium = TextStyle(
        fontFamily = RocGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    titleSmall = TextStyle(
        fontFamily = RocGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = RocGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    labelMedium = TextStyle(
        fontFamily = RocGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
)

// Material 3 typography guideline
//val Typography = Typography(
//    displayLarge = TextStyle(
//        fontFamily = RocGrotesk,
//        fontWeight = FontWeight.Normal,
//        fontSize = 57.sp,
//        lineHeight = 64.sp,
//        letterSpacing = (-0.25).sp,
//    ),
//    displayMedium = TextStyle(
//        fontFamily = RocGrotesk,
//        fontWeight = FontWeight.Normal,
//        fontSize = 45.sp,
//        lineHeight = 52.sp,
//        letterSpacing = 0.sp,
//    ),
//    displaySmall = TextStyle(
//        fontFamily = RocGrotesk,
//        fontWeight = FontWeight.Normal,
//        fontSize = 36.sp,
//        lineHeight = 44.sp,
//        letterSpacing = 0.sp,
//    ),
//    headlineLarge = TextStyle(
//        fontFamily = RocGrotesk,
//        fontWeight = FontWeight.Normal,
//        fontSize = 32.sp,
//        lineHeight = 40.sp,
//        letterSpacing = 0.sp,
//    ),
//    headlineMedium = TextStyle(
//        fontFamily = RocGrotesk,
//        fontWeight = FontWeight.Normal,
//        fontSize = 28.sp,
//        lineHeight = 36.sp,
//        letterSpacing = 0.sp,
//    ),
//    headlineSmall = TextStyle(
//        fontFamily = RocGrotesk,
//        fontWeight = FontWeight.Normal,
//        fontSize = 24.sp,
//        lineHeight = 32.sp,
//        letterSpacing = 0.sp,
//    ),
//    titleLarge = TextStyle(
//        fontFamily = RocGrotesk,
//        fontWeight = FontWeight.Bold,
//        fontSize = 22.sp,
//        lineHeight = 28.sp,
//        letterSpacing = 0.sp,
//    ),
//    titleMedium = TextStyle(
//        fontFamily = RocGrotesk,
//        fontWeight = FontWeight.Bold,
//        fontSize = 18.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.1.sp,
//    ),
//    titleSmall = TextStyle(
//        fontFamily = RocGrotesk,
//        fontWeight = FontWeight.Medium,
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
//        letterSpacing = 0.1.sp,
//    ),
//    bodyLarge = TextStyle(
//        fontFamily = RocGrotesk,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp,
//    ),
//    bodyMedium = TextStyle(
//        fontFamily = RocGrotesk,
//        fontWeight = FontWeight.Normal,
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
//        letterSpacing = 0.25.sp,
//    ),
//    bodySmall = TextStyle(
//        fontFamily = RocGrotesk,
//        fontWeight = FontWeight.Normal,
//        fontSize = 12.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.4.sp,
//    ),
//    labelLarge = TextStyle(
//        fontFamily = RocGrotesk,
//        fontWeight = FontWeight.Medium,
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
//        letterSpacing = 0.1.sp,
//    ),
//    labelMedium = TextStyle(
//        fontFamily = RocGrotesk,
//        fontWeight = FontWeight.Medium,
//        fontSize = 12.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp,
//    ),
//    labelSmall = TextStyle(
//        fontFamily = RocGrotesk,
//        fontWeight = FontWeight.Medium,
//        fontSize = 10.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.sp,
//    ),
