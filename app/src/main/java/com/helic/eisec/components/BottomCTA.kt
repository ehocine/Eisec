package com.helic.eisec.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.helic.eisec.R
import com.helic.eisec.ui.theme.AppTheme

@Composable
fun BottomCTA(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    color: Color,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onShare: () -> Unit,
    onButtonChange: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(AppTheme.colors.card),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = modifier.padding(
                top = AppTheme.dimensions.paddingLarge,
                bottom = AppTheme.dimensions.paddingLarge,
                start = AppTheme.dimensions.paddingXL,
                end = AppTheme.dimensions.paddingXL
            ),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ActionIcons(onEdit = { onEdit() }, onDelete = { onDelete() }, onShare = { onShare() })
            Spacer(modifier = modifier.width(AppTheme.dimensions.paddingLarge))

            Row(modifier = modifier.fillMaxWidth(), Arrangement.End) {
                PrimaryButtonWithIcon(
                    title = title,
                    onclick = {
                        onButtonChange()
                    },
                    icon = icon, color = color
                )
            }
        }
    }
}

@Composable
fun ActionIcons(
    modifier: Modifier = Modifier,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onShare: () -> Unit
) {
    Row(modifier = modifier.wrapContentWidth(), horizontalArrangement = Arrangement.SpaceAround) {

        IconButton(onClick = { onEdit.invoke() }) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(R.string.text_edit_button),
                tint = AppTheme.colors.primary
            )
        }

        IconButton(onClick = { onDelete.invoke() }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.text_delete_button),
                tint = AppTheme.colors.primary
            )
        }

        IconButton(onClick = { onShare.invoke() }) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = stringResource(R.string.text_share_button),
                tint = AppTheme.colors.primary
            )
        }
    }
}