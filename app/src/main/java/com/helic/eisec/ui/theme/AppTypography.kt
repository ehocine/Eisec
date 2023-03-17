package com.helic.eisec.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.helic.eisec.R


val sailec = FontFamily(
    Font(R.font.sailec_regular),
    Font(R.font.sailec_medium, FontWeight.SemiBold),
    Font(R.font.sailec_bold, FontWeight.Bold)
)

data class AppTypography(

    val bigTitle: TextStyle = TextStyle(
        fontFamily = sailec,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp
    ),

    val h1: TextStyle = TextStyle(
        fontFamily = sailec,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),

    val h2: TextStyle = TextStyle(
        fontFamily = sailec,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),

    val subtitle: TextStyle = TextStyle(
        fontFamily = sailec,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),

    val body: TextStyle = TextStyle(
        fontFamily = sailec,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp
    ),

    val button: TextStyle = TextStyle(
        fontFamily = sailec,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),

    val caption: TextStyle = TextStyle(
        fontFamily = sailec,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),

    val overline: TextStyle = TextStyle(
        fontFamily = sailec,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp,
        letterSpacing = 1.25.sp
    )

)

internal val LocalTypography = staticCompositionLocalOf { AppTypography() }