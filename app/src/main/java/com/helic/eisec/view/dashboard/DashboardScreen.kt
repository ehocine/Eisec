package com.helic.eisec.view.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.helic.eisec.R
import com.helic.eisec.components.DashboardCardItem
import com.helic.eisec.model.task.Priority
import com.helic.eisec.ui.theme.AppTheme
import com.helic.eisec.utils.MainActions
import com.helic.eisec.utils.viewstate.ViewState
import com.helic.eisec.view.animationviewstate.AnimationViewState
import com.helic.eisec.view.animationviewstate.ScreenState
import com.helic.eisec.viewmodel.MainViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    modifier: Modifier,
    mainViewModel: MainViewModel,
    actions: MainActions,
    toggleTheme: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.text_my_dashboard),
                        textAlign = TextAlign.Start,
                        style = AppTheme.typography.h1,
                        color = AppTheme.colors.text
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            toggleTheme()
                        }
                    ) {
                        Icon(
                            imageVector = when (isSystemInDarkTheme()) {
                                true -> Icons.Default.DarkMode
                                false -> Icons.Default.LightMode
                            },
                            contentDescription = stringResource(R.string.text_bulb_turn_on),
                            tint = AppTheme.colors.primary
                        )
                    }

                    Spacer(modifier = modifier.width(AppTheme.dimensions.paddingMedium))

                    IconButton(
                        onClick = {
                            actions.gotoAbout.invoke()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = stringResource(R.string.text_bulb_turn_on),
                            tint = AppTheme.colors.primary
                        )
                    }
                },
                backgroundColor = AppTheme.colors.background, elevation = 0.dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = modifier.padding(AppTheme.dimensions.paddingXL),
                onClick = {
                    actions.gotoAddTask.invoke(0, 0)
                },
                backgroundColor = AppTheme.colors.primary,
                contentColor = AppTheme.colors.text,
                elevation = FloatingActionButtonDefaults.elevation(4.dp)
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.text_addTask),
                    tint = AppTheme.colors.white
                )
            }
        }
    ) {

        val listState = rememberLazyListState()
        var urgentCount by remember { mutableStateOf(0) }
        var importantCount by remember { mutableStateOf(0) }
        var delegateCount by remember { mutableStateOf(0) }
        var dumpCount by remember { mutableStateOf(0) }

        when (val allTaskList = mainViewModel.feed.collectAsState().value) {
            ViewState.Empty -> {
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
            ViewState.Loading -> {
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
            is ViewState.Success -> {

                // this will split list into urgent on left & important on right
                val (urgent, important) = allTaskList.task.partition { it.priority.name == Priority.URGENT.name }

                // this will split list into urgent on left & important on right
                val (delegate, dump) = allTaskList.task.partition { it.priority.name == Priority.DELEGATE.name }

                // setting task count to mutable state
                urgentCount = urgent.count { it.priority.name == Priority.URGENT.name }
                importantCount = important.count { it.priority.name == Priority.IMPORTANT.name }
                delegateCount = delegate.count { it.priority.name == Priority.DELEGATE.name }
                dumpCount = dump.count { it.priority.name == Priority.DUMP.name }

                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(bottom = 100.dp),
                    modifier = modifier
                        .background(
                            AppTheme.colors.background
                        )
                        .fillMaxSize()
                ) {

                    item {
                        DashboardCardItem(
                            title = stringResource(R.string.text_do_it_now),
                            description = stringResource(R.string.text_important_and_urgent),
                            count = urgentCount.toString(),
                            color = AppTheme.colors.success,
                            onClick = {
                                actions.gotoAllTask(Priority.URGENT)
                            }
                        )
                    }

                    item {
                        DashboardCardItem(
                            title = stringResource(R.string.text_decide_when_todo),
                            description = stringResource(R.string.text_important_not_urgent),
                            count = importantCount.toString(),
                            color = AppTheme.colors.information,
                            onClick = {
                                actions.gotoAllTask(Priority.IMPORTANT)
                            }
                        )
                    }

                    item {
                        DashboardCardItem(
                            title = stringResource(R.string.text_delegate_it),
                            description = stringResource(R.string.text_urgent_not_important),
                            count = delegateCount.toString(),
                            color = AppTheme.colors.error,
                            onClick = {
                                actions.gotoAllTask(Priority.DELEGATE)
                            }
                        )
                    }

                    item {
                        DashboardCardItem(
                            title = stringResource(R.string.text_dump_it),
                            description = stringResource(R.string.text_not_important_not_urgent),
                            count = dumpCount.toString(),
                            color = AppTheme.colors.warning,
                            onClick = {
                                actions.gotoAllTask(Priority.DUMP)
                            }
                        )
                    }
                }
            }
            is ViewState.Error -> {
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
            else -> Unit
        }
    }
}