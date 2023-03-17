package com.helic.eisec.view.add

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.helic.eisec.R
import com.helic.eisec.components.*
import com.helic.eisec.model.task.Priority
import com.helic.eisec.model.task.task
import com.helic.eisec.ui.theme.AppTheme
import com.helic.eisec.utils.*
import com.helic.eisec.viewmodel.MainViewModel
import com.helic.eisec.workers.scheduleReminders
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddTaskScreen(
    modifier: Modifier,
    viewModel: MainViewModel,
    actions: MainActions,
    defaultUrgency: Int,
    defaultImportance: Int
) {

    // component state
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    // slider points
    val points = listOf("0", "1", "2", "3", "4")

    // task value state
    var taskState by remember {
        mutableStateOf(
            task {
                title = ""
                description = ""
                category = ""
                urgency = defaultUrgency
                importance = defaultImportance
                priority = Priority.IMPORTANT
                due = getLocalDate()
                isCompleted = false
            }
        )
    }

    // Add Task Screen content
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.text_addTask),
                        style = AppTheme.typography.h2,
                        textAlign = TextAlign.Start,
                        color = AppTheme.colors.text,
                        modifier = modifier.padding(start = AppTheme.dimensions.paddingLarge)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { actions.upPress.invoke() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIos,
                            contentDescription = stringResource(R.string.back_button),
                            tint = AppTheme.colors.text
                        )
                    }
                },
                backgroundColor = AppTheme.colors.background, elevation = 0.dp
            )
        }

    ) {

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = AppTheme.dimensions.paddingXXL),
            modifier = modifier
                .background(
                    AppTheme.colors.background
                )
                .fillMaxSize()
        ) {
            // Title
            item {
                Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXXL))
                EisecInputTextField(
                    title = stringResource(R.string.text_title),
                    value = taskState.title
                ) {
                    taskState = taskState.copy(title = it)
                }
            }

            // Description
            item {
                Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXXL))
                EisecInputTextField(
                    title = stringResource(R.string.text_description),
                    value = taskState.description
                ) {
                    taskState = taskState.copy(description = it)
                }
            }

            // Category
            item {
                Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXXL))
                EisecInputTextField(
                    title = stringResource(R.string.text_category),
                    value = taskState.category
                ) {
                    taskState = taskState.copy(category = it)
                }
            }

            // Due Date Time
            item {
                Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXXL))
                EisecInputTextField(
                    modifier = Modifier.clickable {
                        val calendar = getCalendar(taskState.due)
                        context.showDatePicker(calendar) {
                            calendar.set(Calendar.DAY_OF_MONTH, it.get(Calendar.DAY_OF_MONTH))
                            calendar.set(Calendar.MONTH, it.get(Calendar.MONTH))
                            calendar.set(Calendar.YEAR, it.get(Calendar.YEAR))
                            context.showTimePicker(calendar) { timeCalendar ->
                                taskState = taskState.copy(due = formatCalendar(timeCalendar))
                            }
                        }
                    },
                    title = stringResource(R.string.text_due_date_time),
                    value = taskState.due,
                    readOnly = true, enabled = false
                ) {}
            }

            // Urgency
            item {
                Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXXL))
                Column(
                    modifier = modifier.padding(
                        start = AppTheme.dimensions.paddingXXL,
                        end = AppTheme.dimensions.paddingXXL
                    )
                ) {
                    Text(
                        text = stringResource(R.string.text_urgency),
                        style = AppTheme.typography.subtitle,
                        color = AppTheme.colors.text
                    )
                    Spacer(modifier = modifier.height(AppTheme.dimensions.paddingLarge))

                    EisecStepSlider(modifier, points, taskState.urgency.toFloat()) {
                        taskState = taskState.copy(urgency = it)
                    }
                }
            }

            // Importance
            item {
                Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXXL))
                Column(
                    modifier = modifier.padding(
                        start = AppTheme.dimensions.paddingXXL,
                        end = AppTheme.dimensions.paddingXXL
                    )
                ) {
                    Text(
                        text = stringResource(R.string.text_importance),
                        style = AppTheme.typography.subtitle,
                        color = AppTheme.colors.text
                    )
                    Spacer(modifier = modifier.height(AppTheme.dimensions.paddingLarge))

                    EisecStepSlider(modifier, points, taskState.importance.toFloat()) {
                        taskState = taskState.copy(importance = it)
                    }
                }
            }

            // Save Task CTA
            item {
                Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXXXL))
                PrimaryButton(title = stringResource(R.string.text_save_task)) {

                    // calculate the average value by adding urgency + priority / 2
                    val priorityAverage = taskState.importance + taskState.urgency / 2
                    taskState = taskState.copy(priority = calculatePriority(priorityAverage))

                    when {
                        taskState.title.isEmpty() && taskState.description.isEmpty() || taskState.category.isEmpty() -> {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(message = "Please fill all the fields & save the task")
                            }
                        }
                        else -> {
                            viewModel.insertTask(taskState).run {
                                context.scheduleReminders(taskState)
                                showToast(context, "Task added successfully")
                                actions.upPress.invoke()
                            }
                        }
                    }
                }
            }
        }
    }
}
