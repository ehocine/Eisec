package com.helic.eisec.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.helic.eisec.ui.theme.AppTheme
import com.helic.eisec.utils.coloredShadow

@Composable
fun DashboardCardItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    count: String,
    color: Color,
    onClick: () -> Unit
) {
    val gradientBrush = Brush.verticalGradient(listOf(color.copy(.8F), color), startY = 10F)

    Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXXL))

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = AppTheme.dimensions.paddingXL, end = AppTheme.dimensions.paddingXL)
            .coloredShadow(
                color,
                alpha = 0.4F,
                borderRadius = AppTheme.dimensions.paddingXXL,
                shadowRadius = AppTheme.dimensions.paddingMedium,
                offsetX = AppTheme.dimensions.paddingNone,
                offsetY = AppTheme.dimensions.paddingSmall
            )
            .clip(RoundedCornerShape(AppTheme.shapes.shapeXL))
            .background(brush = gradientBrush)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = modifier
                .wrapContentWidth()
                .padding(
                    top = AppTheme.dimensions.paddingXXXL,
                    bottom = AppTheme.dimensions.paddingXXXL
                )
                .align(Alignment.CenterVertically)
        ) {
            Text(text = title, style = AppTheme.typography.h2, color = Color.White)
            Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXL))
            Text(text = description, style = AppTheme.typography.subtitle, color = Color.White)
        }
        Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXL))
        Text(text = count, style = AppTheme.typography.bigTitle, color = Color.White)
    }
}