package com.helic.eisec.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.helic.eisec.ui.theme.AppTheme

@Composable
fun PrimaryButton(modifier: Modifier = Modifier, title: String, onclick: () -> Unit) {

    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(RoundedCornerShape(60.dp))
            .padding(start = AppTheme.dimensions.paddingXL, end = AppTheme.dimensions.paddingXL),
        onClick = { onclick() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = AppTheme.colors.primary,
            contentColor = AppTheme.colors.white
        ),
    ) {
        Text(
            text = title,
            style = AppTheme.typography.button,
            textAlign = TextAlign.Center,
            color = AppTheme.colors.white
        )
    }
}

@Composable
fun PrimaryButtonWithIcon(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    onclick: () -> Unit,
    color: Color
) {
    Button(
        onClick = { onclick() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color,
            contentColor = AppTheme.colors.white
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color.White
        )

        Spacer(modifier = modifier.width(AppTheme.dimensions.paddingLarge))

        Text(
            text = title,
            style = AppTheme.typography.button,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}