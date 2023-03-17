package com.helic.eisec.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.helec.eisec.view.task.AllTaskScreen
import com.helic.eisec.utils.MainActions
import com.helic.eisec.utils.getUrgencyImportanceFromPriority
import com.helic.eisec.view.about.AboutScreen
import com.helic.eisec.view.add.AddTaskScreen
import com.helic.eisec.view.dashboard.DashboardScreen
import com.helic.eisec.view.details.TaskDetailsScreen
import com.helic.eisec.view.edit.EditTaskScreen
import com.helic.eisec.view.webview.WebViewScreen
import com.helic.eisec.viewmodel.MainViewModel

object EndPoints {
    const val ID = "id"
    const val PRIORITY = "priority"
    const val URL = "url"
    const val TITLE = "title"
}

object QueryParams {
    const val URGENCY = "urgency"
    const val IMPORTANCE = "importance"
}

object EisecModifier {
    val modifier: Modifier = Modifier
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(toggleTheme: () -> Unit) {
    val navController = rememberAnimatedNavController()
    val actions = remember(navController) { MainActions(navController) }

    AnimatedNavHost(
        navController,
        startDestination = Screens.Dashboard.route
    ) {

        composable(
            Screens.Dashboard.route
        ) {
            val viewModel: MainViewModel = viewModel(
                factory = HiltViewModelFactory(LocalContext.current, it)
            )
            viewModel.getAllTask()
            DashboardScreen(
                EisecModifier.modifier,
                viewModel,
                actions,
                toggleTheme
            )
        }

        composable(
            "${Screens.AddTask.route}?urgency={urgency}&importance={importance}",
            arguments = listOf(
                navArgument(QueryParams.URGENCY) {
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument(QueryParams.IMPORTANCE) {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {
            val viewModel = hiltViewModel<MainViewModel>(it)

            val defaultUrgency = it.arguments?.getInt(QueryParams.URGENCY) ?: 0
            val defaultImportance = it.arguments?.getInt(QueryParams.IMPORTANCE) ?: 0

            AddTaskScreen(
                EisecModifier.modifier,
                viewModel,
                actions,
                defaultUrgency,
                defaultImportance
            )
        }

        composable(
            "${Screens.AllTask.route}/{priority}",
            arguments = listOf(navArgument(EndPoints.PRIORITY) { type = NavType.StringType })
        ) {
            val viewModel = hiltViewModel<MainViewModel>(it)

            val priority = it.arguments?.getString(EndPoints.PRIORITY)
                ?: throw IllegalStateException("'Priority' shouldn't be null")

            viewModel.getTaskByPriority(priority = priority)

            val defaultUrgencyImportance = getUrgencyImportanceFromPriority(priority)

            AllTaskScreen(
                EisecModifier.modifier,
                viewModel,
                actions,
                defaultUrgencyImportance.first,
                defaultUrgencyImportance.second
            )
        }

        composable(
            "${Screens.TaskDetails.route}/{id}",
            arguments = listOf(navArgument(EndPoints.ID) { type = NavType.IntType })
        ) {
            val viewModel = hiltViewModel<MainViewModel>(it)
            val taskID = it.arguments?.getInt(EndPoints.ID)
                ?: throw IllegalStateException("'Task ID' shouldn't be null")

            viewModel.findTaskByID(taskID)
            TaskDetailsScreen(EisecModifier.modifier, viewModel, actions)
        }


        composable(
            "${Screens.EditTask.route}/{id}",
            arguments = listOf(navArgument(EndPoints.ID) { type = NavType.IntType })
        ) {
            val viewModel = hiltViewModel<MainViewModel>(it)
            val taskID = it.arguments?.getInt(EndPoints.ID)
                ?: throw IllegalStateException("'Task ID' shouldn't be null")

            viewModel.findTaskByID(taskID)
            EditTaskScreen(EisecModifier.modifier, viewModel, actions)
        }


        composable(
            Screens.About.route
        ) {
            AboutScreen(EisecModifier.modifier, actions)
        }

        composable(
            "${Screens.WebView.route}/{title}/{url}",
            arguments = listOf(
                navArgument(EndPoints.TITLE) { type = NavType.StringType },
                navArgument(EndPoints.URL) { type = NavType.StringType }
            )
        ) {
            val url = it.arguments?.getString(EndPoints.URL)
                ?: throw IllegalStateException("'URL' shouldn't be null")
            val title = it.arguments?.getString(EndPoints.TITLE)
                ?: throw java.lang.IllegalStateException("'Title' shouldn't be null")
            WebViewScreen(
                modifier = EisecModifier.modifier,
                title = title,
                url = url,
                actions = actions
            )
        }
    }
}