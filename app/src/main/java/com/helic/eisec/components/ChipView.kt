package com.helic.eisec.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.helic.eisec.ui.theme.AppTheme

@Composable
fun ChipView(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .wrapContentWidth()
            .animateContentSize()
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(AppTheme.shapes.shapeLarge))
            .background(AppTheme.colors.primary)
    ) {
        Text(
            text = title,
            modifier = modifier.padding(
                AppTheme.dimensions.paddingLarge,
                AppTheme.dimensions.paddingSmall,
                AppTheme.dimensions.paddingLarge,
                AppTheme.dimensions.paddingSmall
            ),
            style = AppTheme.typography.overline,
            color = AppTheme.colors.white
        )
    }
}

@Composable
fun SmallChipView(
    modifier: Modifier = Modifier,
    title: String,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .wrapContentWidth()
            .animateContentSize()
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(AppTheme.shapes.shapeLarge))
            .background(color = color.copy(0.10F))
    ) {
        Text(
            text = title,
            modifier = modifier.padding(
                start = AppTheme.dimensions.paddingSmall,
                end = AppTheme.dimensions.paddingSmall,
                top = AppTheme.dimensions.paddingSmall,
                bottom = AppTheme.dimensions.paddingSmall
            ),
            style = MaterialTheme.typography.overline,
            color = color
        )
    }
}

