package com.helic.eisec.view.details

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ShareCompat
import com.helic.eisec.R
import com.helic.eisec.components.BottomCTA
import com.helic.eisec.components.ChipView
import com.helic.eisec.components.InfoCard
import com.helic.eisec.model.task.Priority
import com.helic.eisec.model.task.task
import com.helic.eisec.ui.theme.AppTheme
import com.helic.eisec.utils.MainActions
import com.helic.eisec.utils.viewstate.SingleViewState
import com.helic.eisec.view.animationviewstate.AnimationViewState
import com.helic.eisec.view.animationviewstate.ScreenState
import com.helic.eisec.viewmodel.MainViewModel
import com.helic.eisec.workers.cancelReminder
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskDetailsScreen(
    modifier: Modifier,
    viewModel: MainViewModel,
    action: MainActions
) {

    val context = LocalContext.current

    val activity = LocalContext.current as Activity

    var taskState by remember {
        mutableStateOf(
            task {
                title = ""
                description = ""
                category = ""
                urgency = 0
                importance = 0
                priority = Priority.IMPORTANT
                due = "18/12/1998"
                isCompleted = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.text_taskDetails),
                        style = AppTheme.typography.h2,
                        textAlign = TextAlign.Start,
                        color = AppTheme.colors.text,
                        modifier = modifier.padding(start = 16.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { action.upPress.invoke() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIos,
                            contentDescription = stringResource(R.string.back_button),
                            tint = AppTheme.colors.primary
                        )
                    }
                },
                backgroundColor = AppTheme.colors.background, elevation = 0.dp
            )
        },
        bottomBar = {

            val buttonColor by animateColorAsState(if (taskState.isCompleted) AppTheme.colors.error else AppTheme.colors.success)

            val buttonTitle = when (taskState.isCompleted) {
                true -> stringResource(id = R.string.text_incomplete)
                false -> stringResource(id = R.string.text_complete)
            }

            val buttonIcon = when (taskState.isCompleted) {
                true -> Icons.Default.Close
                false -> Icons.Default.Check
            }

            BottomCTA(
                onEdit = {
                    action.gotoEditTask(taskState.id)
                },
                onDelete = {
                    viewModel.deleteTaskByID(taskState.id).run {
                        context.cancelReminder(taskState)
                        action.upPress.invoke()
                    }
                },
                onShare = {
                    val createdAt = Date(taskState.createdAt) // Date
                    shareNote(
                        activity = activity,
                        taskState.title,
                        taskState.description,
                        taskState.category,
                        taskState.priority.name,
                        createdAt,
                    )
                },
                onButtonChange = {
                    viewModel.updateStatus(taskState.id, !taskState.isCompleted)
                },
                title = buttonTitle, icon = buttonIcon, color = buttonColor
            )
        }
    ) {
        val listState = rememberLazyListState()

        when (val result = viewModel.singleTask.collectAsState().value) {

            is SingleViewState.Success -> {

                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .background(AppTheme.colors.background)
                        .padding(start = 16.dp, end = 16.dp),
                    state = listState,
                    contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
                ) {

                    // update the task state with latest value
                    val task = result.task
                    taskState = task

                    item {
                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .clip(RoundedCornerShape(12.dp))
                                .background(AppTheme.colors.card),
                            contentAlignment = Alignment.TopCenter
                        ) {

                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = AppTheme.dimensions.paddingXL,
                                        end = AppTheme.dimensions.paddingXL,
                                        bottom = AppTheme.dimensions.paddingXXL,
                                        top = AppTheme.dimensions.paddingXXL
                                    )
                            ) {

                                Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXL))

                                // Category chip
                                ChipView(
                                    title = task.category,
                                    onClick = {
                                        // Do nothing...
                                    }
                                )

                                Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXL))

                                // Title
                                Text(
                                    text = task.title,
                                    style = AppTheme.typography.h1,
                                    textAlign = TextAlign.Start,
                                    color = AppTheme.colors.text
                                )

                                Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXXL))

                                // Description
                                Text(
                                    text = task.description,
                                    style = AppTheme.typography.body,
                                    textAlign = TextAlign.Start,
                                    color = AppTheme.colors.text
                                )

                                Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXXL))

                                // Priority score card
                                Row(
                                    modifier = modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    val weight = modifier.weight(1f)
                                    InfoCard(
                                        title = stringResource(R.string.text_urgency),
                                        value = task.urgency.toString(),
                                        modifier = weight
                                    )

                                    Spacer(modifier = modifier.width(AppTheme.dimensions.paddingLarge))

                                    InfoCard(
                                        title = stringResource(R.string.text_importance),
                                        value = task.importance.toString(),
                                        modifier = weight
                                    )
                                }
                            }
                        }
                    }
                }
            }
            SingleViewState.Empty -> {
                AnimationViewState(
                    modifier,
                    title = stringResource(R.string.text_no_task_title),
                    description = stringResource(R.string.text_no_task_description),
                    callToAction = stringResource(R.string.text_add_a_task),
                    ScreenState.EMPTY,
                    actions = {
                        action.gotoAddTask.invoke(0, 0)
                    }
                )
            }
            is SingleViewState.Error -> {
                AnimationViewState(
                    modifier,
                    title = stringResource(R.string.text_error_title),
                    description = stringResource(
                        R.string.text_error_description
                    ),
                    callToAction = stringResource(R.string.text_add_a_task),
                    ScreenState.ERROR,
                    actions = {
                        action.gotoAddTask.invoke(0, 0)
                    }
                )
            }
            SingleViewState.Loading -> {
                AnimationViewState(
                    modifier,
                    title = stringResource(R.string.text_no_task_title),
                    description = stringResource(R.string.text_no_task_description),
                    callToAction = stringResource(R.string.text_add_a_task),
                    ScreenState.LOADING,
                    actions = {
                        action.gotoAddTask.invoke(0, 0)
                    }
                )
            }
        }
    }
}

fun shareNote(
    activity: Activity,
    title: String,
    description: String,
    category: String,
    priority: String,
    createdAt: Date
) {
    val shareMsg = activity.getString(
        R.string.text_message_share,
        title,
        description,
        category,
        priority,
        createdAt
    )

    val intent = ShareCompat.IntentBuilder(activity)
        .setType("text/plain")
        .setText(shareMsg)
        .intent

    activity.startActivity(Intent.createChooser(intent, null))
}
