package com.helic.eisec.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember


object AppTheme {

    /**
     * Get [LocalColors] from the AppColors
     */
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    /**
     * Get [LocalTypography] from the AppTypography
     */
    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    /**
     * Get [LocalDimensions] from the AppDimensions
     */
    val dimensions: AppDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensions.current

    /**
     * Get [LocalShapes] from the AppShapes
     */
    val shapes: AppShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    @Composable
    fun AppTheme(
        colors: AppColors = AppTheme.colors,
        typography: AppTypography = AppTheme.typography,
        dimensions: AppDimensions = AppTheme.dimensions,
        shapes: AppShapes = AppTheme.shapes,
        content: @Composable () -> Unit
    ) {

        val rememberColors = remember {
            colors.copy()
        }.apply {
            updateColorsFrom(colors)
        }

        // / overwrite the existing values here
        CompositionLocalProvider(
            LocalColors provides rememberColors,
            LocalTypography provides typography,
            LocalDimensions provides dimensions,
            LocalShapes provides shapes
        ) {
            content()
        }
    }
}