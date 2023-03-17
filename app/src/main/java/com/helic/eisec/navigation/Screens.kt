package com.helic.eisec.navigation

sealed class Screens(val route: String) {
    object Dashboard : Screens("dashboard")
    object AddTask : Screens("add_task")
    object EditTask : Screens("edit_task")
    object AllTask : Screens("all_task")
    object TaskDetails : Screens("details")
    object About : Screens("about")
    object WebView : Screens("webview")
    object AllEmoji : Screens("all_emoji")
}
