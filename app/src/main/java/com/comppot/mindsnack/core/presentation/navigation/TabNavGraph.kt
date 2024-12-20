package com.comppot.mindsnack.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.comppot.mindsnack.articles.presentation.home.HomeScreen
import com.comppot.mindsnack.profile.presentation.profile.ProfileScreen
import com.comppot.mindsnack.articles.presentation.saved.SavedScreen
import com.comppot.mindsnack.articles.presentation.search.SearchScreen
import com.comppot.mindsnack.core.presentation.Screen

@Composable
fun TabNavGraph(
    navController: NavHostController,
    openArticle: (Long) -> Unit,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Tab.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Tab.Home.route) { HomeScreen(openArticle) }
        composable(Screen.Tab.Search.route) { SearchScreen(openArticle) }
        composable(Screen.Tab.Saved.route) { SavedScreen(openArticle) }
        composable(Screen.Tab.Profile.route) { ProfileScreen() }
    }
}
