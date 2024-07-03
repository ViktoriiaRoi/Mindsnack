package com.comppot.mindsnack.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.comppot.mindsnack.ui.navigation.MainNavGraph
import com.comppot.mindsnack.ui.navigation.Screen
import com.comppot.mindsnack.ui.theme.MindSnackTheme

@Composable
fun MindSnackApp() {
    MindSnackTheme {
        val navController = rememberNavController()
        MainNavGraph(navController, Screen.Login)
    }
}
