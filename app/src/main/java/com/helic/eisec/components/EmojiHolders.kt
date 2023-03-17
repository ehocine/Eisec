package com.helic.eisec.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.helic.eisec.ui.theme.AppTheme

@Composable
fun EmojiPlaceHolder(modifier: Modifier = Modifier, emoji: String, onSelect: () -> Unit) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(AppTheme.colors.card)
            .clickable { onSelect() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            style = AppTheme.typography.bigTitle,
            textAlign = TextAlign.Center,
            color = AppTheme.colors.text
        )
    }
}

@Composable
fun EmojiPlaceHolderSmall(
    modifier: Modifier = Modifier,
    emoji: String,
    onSelect: (String) -> Unit
) {
    Box(
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(AppTheme.colors.card)
            .clickable { onSelect(emoji) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            style = AppTheme.typography.h1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun EmojiPlaceHolderBottomSheet(
    modifier: Modifier = Modifier,
    emoji: String,
    onSelect: (String) -> Unit
) {
    Box(
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape)
            .clickable { onSelect(emoji) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            style = AppTheme.typography.h1,
            color = AppTheme.colors.text,
            textAlign = TextAlign.Center
        )
    }
}

