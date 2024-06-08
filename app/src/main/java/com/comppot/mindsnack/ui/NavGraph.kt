package com.comppot.mindsnack.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.comppot.mindsnack.R
import com.comppot.mindsnack.ui.home.HomeScreen
import com.comppot.mindsnack.ui.profile.ProfileScreen
import com.comppot.mindsnack.ui.saved.SavedScreen
import com.comppot.mindsnack.ui.search.SearchScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) { HomeScreen() }
        composable(route = Screen.Search.route) { SearchScreen() }
        composable(route = Screen.Saved.route) { SavedScreen() }
        composable(route = Screen.Profile.route) { ProfileScreen() }
    }
}

sealed class Screen(val route: String, @StringRes val label: Int, val filledIcon: ImageVector, val outlinedIcon: ImageVector) {
    object Home : Screen("home", R.string.screen_home, Icons.Filled.Home, Icons.Outlined.Home)
    object Search : Screen("search", R.string.screen_search, Icons.Filled.Search, Icons.Outlined.Search)
    object Saved : Screen("saved", R.string.screen_saved, Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder)
    object Profile : Screen("profile", R.string.screen_profile, Icons.Filled.Person, Icons.Outlined.Person)
}
