package com.helic.eisec.view.edit

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
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
import com.helic.eisec.utils.viewstate.SingleViewState
import com.helic.eisec.view.animationviewstate.AnimationViewState
import com.helic.eisec.view.animationviewstate.ScreenState
import com.helic.eisec.viewmodel.MainViewModel
import com.helic.eisec.workers.scheduleReminders
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditTaskScreen(modifier: Modifier, viewModel: MainViewModel, actions: MainActions) {

    // Coroutines scope
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // slider points
    val points = listOf("0", "1", "2", "3", "4")

    // List, Scaffold and bottom sheet state
    val listState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()

    // All Task State
    var taskID by rememberSaveable { mutableStateOf(0) }
    var titleState by rememberSaveable { mutableStateOf("") }
    var descriptionState by rememberSaveable { mutableStateOf("") }
    var categoryState by remember { mutableStateOf("") }
    var emojiState by remember { mutableStateOf("") }
    var urgencyState by remember { mutableStateOf(0) }
    var importanceState by remember { mutableStateOf(0) }
    var dueState by remember { mutableStateOf("") }
    var priorityState by remember { mutableStateOf(Priority.IMPORTANT) }
    var isCompletedState by remember { mutableStateOf(false) }
    var createdAtState by remember { mutableStateOf(0L) }
    val updatedAtState by remember { mutableStateOf(System.currentTimeMillis()) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.text_editTask),
                        style = AppTheme.typography.h1,
                        textAlign = TextAlign.Start,
                        color = AppTheme.colors.text,
                        modifier = Modifier.padding(start = AppTheme.dimensions.paddingXL)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { actions.upPress.invoke() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIos,
                            contentDescription = stringResource(R.string.back_button),
                            tint = AppTheme.colors.primary
                        )
                    }
                },
                backgroundColor = AppTheme.colors.background, elevation = 0.dp
            )
        }
    ) {

        when (val taskResult = viewModel.singleTask.collectAsState().value) {
            SingleViewState.Empty -> {
                AnimationViewState(
                    modifier,
                    title = stringResource(R.string.text_no_task_title),
                    description = stringResource(R.string.text_no_task_description),
                    callToAction = stringResource(R.string.text_add_a_task),
                    ScreenState.EMPTY,
                    actions = {
                        actions.gotoAddTask.invoke(0, 0)
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
                        actions.gotoAddTask.invoke(0, 0)
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
                        actions.gotoAddTask.invoke(0, 0)
                    }
                )
            }
            is SingleViewState.Success -> {

                // update the task state with latest value
                with(taskResult.task) {
                    taskID = this.id
                    titleState = this.title
                    descriptionState = this.description
                    categoryState = this.category
                    urgencyState = this.urgency
                    importanceState = this.importance
                    priorityState = this.priority
                    dueState = this.due
                    isCompletedState = this.isCompleted
                    createdAtState = this.createdAt
                }
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(bottom = AppTheme.dimensions.paddingXXL),
                    modifier = modifier
                        .fillMaxSize()
                        .background(
                            AppTheme.colors.background
                        )
                ) {

//                    // Emoji
//                    item {
//                        Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXXL))
//                        Box(
//                            modifier = Modifier.fillMaxWidth(),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            EmojiPlaceHolder(
//                                emoji = emojiState,
//                                onSelect = {
//                                    scope.launch {
//                                        actions.gotoAllEmoji.invoke()
//                                    }
//                                }
//                            )
//                        }
//                    }

                    // Title
                    item {
                        Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXXL))
                        EisecInputTextField(
                            title = stringResource(R.string.text_title),
                            value = titleState
                        ) {
                            titleState = it
                        }
                    }

                    // Description
                    item {
                        Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXXL))
                        EisecInputTextField(
                            title = stringResource(R.string.text_description),
                            value = descriptionState
                        ) {
                            descriptionState = it
                        }
                    }

                    // Category
                    item {
                        Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXXL))
                        EisecInputTextField(
                            title = stringResource(R.string.text_category),
                            value = categoryState
                        ) {
                            categoryState = it
                        }
                    }

                    // Due Date Time
                    item {
                        Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXXL))
                        EisecInputTextField(
                            modifier = Modifier.clickable {
                                val calendar = getCalendar(dueState)
                                context.showDatePicker(calendar) {
                                    calendar.set(
                                        Calendar.DAY_OF_MONTH,
                                        it.get(Calendar.DAY_OF_MONTH)
                                    )
                                    calendar.set(Calendar.MONTH, it.get(Calendar.MONTH))
                                    calendar.set(Calendar.YEAR, it.get(Calendar.YEAR))
                                    context.showTimePicker(calendar) { timeCalendar ->
                                        dueState = formatCalendar(timeCalendar)
                                    }
                                }
                            },
                            title = stringResource(R.string.text_due_date_time),
                            value = dueState,
                            readOnly = true, enabled = false, {}
                        )
                    }

                    // Urgency
                    item {
                        Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXXL))
                        Column(
                            modifier = Modifier.padding(
                                start = AppTheme.dimensions.paddingXXL,
                                end = AppTheme.dimensions.paddingXXL
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.text_urgency),
                                style = AppTheme.typography.subtitle,
                                color = AppTheme.colors.text
                            )
                            Spacer(modifier = Modifier.height(AppTheme.dimensions.paddingLarge))

                            EisecStepSlider(modifier, points, urgencyState.toFloat()) {
                                urgencyState = it
                            }
                        }
                    }

                    // Importance
                    item {
                        Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXXL))
                        Column(
                            modifier = Modifier.padding(
                                start = AppTheme.dimensions.paddingXXL,
                                end = AppTheme.dimensions.paddingXXL
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.text_importance),
                                style = AppTheme.typography.subtitle,
                                color = AppTheme.colors.text
                            )
                            Spacer(modifier = Modifier.height(AppTheme.dimensions.paddingLarge))

                            EisecStepSlider(modifier, points, importanceState.toFloat()) {
                                importanceState = it
                            }
                        }
                    }

                    // Update Task CTA
                    item {

                        Spacer(modifier = Modifier.height(AppTheme.dimensions.paddingXXL))
                        PrimaryButton(title = stringResource(R.string.text_save_task)) {

                            // calculate the average value by adding urgency + priority / 2
                            val priorityAverage = importanceState + urgencyState / 2
                            priorityState = calculatePriority(priorityAverage)

                            val task = task {
                                title = titleState
                                description = descriptionState
                                category = categoryState
                                emoji = emojiState
                                urgency = urgencyState
                                importance = importanceState
                                priority = priorityState
                                due = dueState
                                isCompleted = isCompletedState
                                createdAt = createdAtState
                                updatedAt = updatedAtState
                                id = taskID
                            }

                            when {
                                titleState.isEmpty() && descriptionState.isEmpty() || categoryState.isEmpty() -> {
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar("Please fill all the fields & save the task")
                                    }
                                }
                                else -> {
                                    viewModel.insertTask(task).run {

                                        context.scheduleReminders(task)
                                        showToast(context, "Task updated successfully!")
                                        actions.upPress.invoke()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun BottomSheetTitle() {
    Text(
        modifier = Modifier.padding(
            start = AppTheme.dimensions.paddingXL,
            top = AppTheme.dimensions.paddingXL,
            bottom = AppTheme.dimensions.paddingXXL
        ),
        text = stringResource(R.string.tetxt_choose_emoji),
        style = AppTheme.typography.h1,
        textAlign = TextAlign.Start,
        color = AppTheme.colors.text
    )
}
