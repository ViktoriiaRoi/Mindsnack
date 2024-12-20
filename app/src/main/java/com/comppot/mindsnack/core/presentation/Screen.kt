package com.comppot.mindsnack.core.presentation

sealed class Screen(val route: String) {
    object Login : Screen("login")

    object Tab : Screen("tab") {
        object Home : Screen("home")
        object Search : Screen("search")
        object Saved : Screen("saved")
        object Profile : Screen("profile")
    }

    object Article : Screen("article")
    object Notifications : Screen("notifications")
}
