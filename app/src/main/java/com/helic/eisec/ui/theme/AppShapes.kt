package com.helic.eisec.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


data class AppShapes(
    val shapeSmall: Dp = 4.dp,
    val shapeMedium: Dp = 8.dp,
    val shapeLarge: Dp = 12.dp,
    val shapeXL: Dp = 24.dp
)

internal val LocalShapes = staticCompositionLocalOf { AppShapes() }
