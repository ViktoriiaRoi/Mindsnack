package com.comppot.mindsnack.core.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.comppot.mindsnack.core.presentation.components.CustomSnackBar
import com.comppot.mindsnack.core.presentation.components.TabBottomBar
import com.comppot.mindsnack.core.presentation.components.TabTopBar
import com.comppot.mindsnack.core.presentation.Screen

@Composable
fun BaseTabScreen(navController: NavHostController, unreadNotifications: Int, onLogout: () -> Unit) {
    val bottomNavController = rememberNavController()
    Scaffold(
        topBar = {
            TabTopBar(
                bottomNavController,
                navigateTo = { navController.navigate(it.route) },
                unreadNotifications = unreadNotifications,
                logout = onLogout
            )
        },
        bottomBar = { TabBottomBar(bottomNavController) },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        snackbarHost = { CustomSnackBar() }
    ) { innerPadding ->
        TabNavGraph(
            bottomNavController,
            openArticle = { navController.navigate("${Screen.Article.route}/$it") },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = 2.dp)
        )
    }
}
