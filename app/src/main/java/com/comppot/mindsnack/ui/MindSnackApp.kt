package com.comppot.mindsnack.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.comppot.mindsnack.ui.components.BottomNavigationBar
import com.comppot.mindsnack.ui.theme.MindSnackTheme

@Composable
fun MindSnackApp() {
    MindSnackTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { innerPadding ->
            NavGraph(navController, Modifier.padding(innerPadding))
        }
    }
}