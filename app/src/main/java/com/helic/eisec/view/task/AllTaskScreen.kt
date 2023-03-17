package com.helec.eisec.view.task

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.helic.eisec.R
import com.helic.eisec.components.TaskItemCard
import com.helic.eisec.model.task.Task
import com.helic.eisec.ui.theme.AppTheme
import com.helic.eisec.utils.MainActions
import com.helic.eisec.utils.viewstate.ViewState
import com.helic.eisec.view.animationviewstate.AnimationViewState
import com.helic.eisec.view.animationviewstate.ScreenState
import com.helic.eisec.viewmodel.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AllTaskScreen(
    modifier: Modifier,
    viewModel: MainViewModel,
    actions: MainActions,
    defaultUrgency: Int = 0,
    defaultImportance: Int = 0
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.text_allTask),
                        style = AppTheme.typography.h2,
                        textAlign = TextAlign.Start,
                        color = AppTheme.colors.text,
                        modifier = modifier.padding(start = 16.dp),
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
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = modifier.padding(AppTheme.dimensions.paddingXL),
                onClick = {
                    actions.gotoAddTask.invoke(defaultUrgency, defaultImportance)
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
        },
        modifier = modifier.background(AppTheme.colors.background)
    ) {

        when (val result = viewModel.feed.collectAsState().value) {
            ViewState.Loading -> {
                AnimationViewState(
                    modifier,
                    title = stringResource(R.string.text_no_task_title),
                    description = stringResource(R.string.text_no_task_description),
                    callToAction = stringResource(R.string.text_add_a_task),
                    ScreenState.LOADING,
                    actions = {
                        actions.gotoAddTask.invoke(defaultUrgency, defaultImportance)
                    }
                )
            }
            ViewState.Empty -> {
                AnimationViewState(
                    modifier,
                    title = stringResource(R.string.text_no_task_title),
                    description = stringResource(R.string.text_no_task_description),
                    callToAction = stringResource(R.string.text_add_a_task),
                    ScreenState.EMPTY,
                    actions = {
                        actions.gotoAddTask.invoke(defaultUrgency, defaultImportance)
                    }
                )
            }
            is ViewState.Success -> {
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp
                    ),
                    modifier = modifier
                        .fillMaxSize()
                        .background(AppTheme.colors.background)
                ) {
                    itemsIndexed(result.task) { _: Int, item: Task ->
                        TaskItemCard(
                            modifier,
                            item,
                            onClick = {
                                actions.gotoTaskDetails(item.id)
                            },
                            onCheckboxChange = {
                                viewModel.updateStatus(item.id, it)
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
                        actions.gotoAddTask.invoke(defaultUrgency, defaultImportance)
                    }
                )
            }
        }
    }
}
