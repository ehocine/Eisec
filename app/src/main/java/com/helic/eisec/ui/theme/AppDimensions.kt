package com.helic.eisec.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


data class AppDimensions(
    val paddingNone: Dp = 0.dp,
    val paddingSmall: Dp = 4.dp,
    val paddingMedium: Dp = 8.dp,
    val paddingLarge: Dp = 12.dp,
    val paddingXL: Dp = 16.dp,
    val paddingXXL: Dp = 24.dp,
    val paddingXXXL: Dp = 36.dp
)

internal val LocalDimensions = staticCompositionLocalOf { AppDimensions() }
