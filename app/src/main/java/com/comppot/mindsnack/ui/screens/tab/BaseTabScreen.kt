package com.comppot.mindsnack.ui.screens.tab

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.comppot.mindsnack.ui.components.CustomSnackBar
import com.comppot.mindsnack.ui.components.TabBottomBar
import com.comppot.mindsnack.ui.components.TabTopBar
import com.comppot.mindsnack.ui.navigation.Screen
import com.comppot.mindsnack.ui.navigation.TabNavGraph

@Composable
fun BaseTabScreen(navController: NavHostController, onLogout: () -> Unit) {
    val bottomNavController = rememberNavController()
    Scaffold(
        topBar = {
            TabTopBar(
                bottomNavController,
                navigateTo = { navController.navigate(it.route) },
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
