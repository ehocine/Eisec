package com.helic.eisec.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helic.eisec.model.task.Task
import com.helic.eisec.ui.theme.AppTheme
import com.helic.eisec.ui.theme.sailec
import com.helic.eisec.utils.convertTimeStampToDate


@Composable
fun TaskItemCard(
    modifier: Modifier = Modifier,
    task: Task,
    onClick: () -> Unit,
    onCheckboxChange: (Boolean) -> Unit
) {

    Spacer(modifier = modifier.height(AppTheme.dimensions.paddingLarge))
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // checkbox state
        val status = remember { mutableStateOf(task.isCompleted) }

        /**
         * Checkbox
         */
        EisecCheckBox(
            value = status.value,
            onValueChanged = {
                status.value = it
                onCheckboxChange(status.value)
            }
        )

        Spacer(modifier = modifier.width(AppTheme.dimensions.paddingLarge))

        /**
         * Emoji + (title + category)
         */
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(AppTheme.dimensions.paddingLarge))
                .background(AppTheme.colors.card)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            /**
             * Emoji Text View
             */
//            EmojiTextView()
            Spacer(modifier = modifier.width(AppTheme.dimensions.paddingLarge))
            /**
             * Title + category
             */
            Column(
                modifier = modifier
                    .align(Alignment.CenterVertically)
                    .padding(AppTheme.dimensions.paddingLarge),
            ) {
                Text(
                    text = task.title,
                    style = when (task.isCompleted) {
                        true -> TextStyle(
                            textDecoration = TextDecoration.LineThrough,
                            fontSize = 16.sp,
                            fontFamily = sailec,
                            fontWeight = FontWeight.SemiBold
                        )
                        false -> AppTheme.typography.subtitle
                    },
                    color = AppTheme.colors.text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = modifier.height(AppTheme.dimensions.paddingLarge))
                Text(
                    text = task.category,
                    style = when (task.isCompleted) {
                        true -> TextStyle(
                            textDecoration = TextDecoration.LineThrough,
                            fontSize = 12.sp,
                            fontFamily = sailec,
                            fontWeight = FontWeight.Normal
                        )
                        false -> AppTheme.typography.caption
                    },
                    color = AppTheme.colors.text.copy(.7f)
                )
                Spacer(modifier = modifier.height(AppTheme.dimensions.paddingLarge))
                Text(
                    text = "Created at: ${convertTimeStampToDate(task.createdAt)}",
                    style = when (task.isCompleted) {
                        true -> TextStyle(
                            textDecoration = TextDecoration.LineThrough,
                            fontSize = 12.sp,
                            fontFamily = sailec,
                            fontWeight = FontWeight.Normal
                        )
                        false -> AppTheme.typography.caption
                    },
                    color = AppTheme.colors.text.copy(.7f)
                )
            }
        }
    }
}

@Composable
fun EmojiTextView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(80.dp)
            .padding(AppTheme.dimensions.paddingLarge)
            .clip(CircleShape)
            .clickable { }
            .background(AppTheme.colors.background)
    ) {
    }
}

@Composable
fun EisecCheckBox(value: Boolean, onValueChanged: (Boolean) -> Unit) {
    Checkbox(
        checked = value,
        onCheckedChange = {
            onValueChanged(it)
        },
        colors = CheckboxDefaults.colors(
            uncheckedColor = AppTheme.colors.primary,
            checkedColor = AppTheme.colors.primary,
            checkmarkColor = AppTheme.colors.white
        )
    )
}
