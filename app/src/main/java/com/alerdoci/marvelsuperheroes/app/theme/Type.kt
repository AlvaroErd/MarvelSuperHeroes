package com.alerdoci.marvelsuperheroes.app.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.alerdoci.marvelsuperheroes.R

// Set of Material typography styles to start with
val marvelTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    )
)
val montserratFont = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.montserrat_bold,
            weight = FontWeight.Bold
        ),
        Font(
            resId = R.font.montserrat_extra_bold,
            weight = FontWeight.ExtraBold
        ),
        Font(
            resId = R.font.montserrat_extra_light,
            weight = FontWeight.ExtraLight
        ),
        Font(
            resId = R.font.montserrat_light,
            weight = FontWeight.Light
        ),
        Font(
            resId = R.font.montserrat_medium,
            weight = FontWeight.Medium
        ),
        Font(
            resId = R.font.montserrat_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resId = R.font.montserrat_semi_bold,
            weight = FontWeight.SemiBold
        ),
        Font(
            resId = R.font.montserrat_thin,
            weight = FontWeight.Thin
        ),
    )
)