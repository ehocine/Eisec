package com.helic.eisec.utils

import androidx.navigation.NavController
import com.helic.eisec.model.task.Priority
import com.helic.eisec.navigation.Screens

class MainActions(navController: NavController) {

    val upPress: () -> Unit = {
        navController.navigateUp()
    }

    val popBackStack: () -> Unit = {
        navController.popBackStack()
    }

    val gotoDashboard: () -> Unit = {
        navController.navigate(Screens.Dashboard.route)
    }

    val gotoAddTask: (urgency: Int?, importance: Int?) -> Unit = { urgency, importance ->
        navController.navigate("${Screens.AddTask.route}?urgency=$urgency&importance=$importance")
    }

    val gotoAllTask: (priority: Priority) -> Unit = { priority ->
        navController.navigate("${Screens.AllTask.route}/${priority.name}")
    }

    val gotoTaskDetails: (id: Int) -> Unit = { id ->
        navController.navigate("${Screens.TaskDetails.route}/$id")
    }

    val gotoEditTask: (id: Int) -> Unit = { id ->
        navController.navigate("${Screens.EditTask.route}/$id")
    }

    val gotoAbout: () -> Unit = {
        navController.navigate(Screens.About.route)
    }

    val gotoWebView: (title: String, url: String) -> Unit = { title, url ->
        navController.navigate("${Screens.WebView.route}/$title/$url")
    }

    val gotoAllEmoji: () -> Unit = {
        navController.navigate(Screens.AllEmoji.route)
    }
}