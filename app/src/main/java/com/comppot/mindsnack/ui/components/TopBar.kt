@file:OptIn(ExperimentalMaterial3Api::class)

package com.comppot.mindsnack.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.comppot.mindsnack.R
import com.comppot.mindsnack.ui.navigation.Screen

@Composable
fun TabTopBar(bottomNavController: NavController, navigateTo: (Screen) -> Unit) {
    TopAppBar(
        title = {
            val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val titleId = when (currentRoute) {
                Screen.Tab.Home.route -> R.string.screen_home
                Screen.Tab.Search.route -> R.string.screen_search
                Screen.Tab.Saved.route -> R.string.screen_saved
                Screen.Tab.Profile.route -> R.string.screen_profile
                else -> R.string.app_name
            }

            TopBarTitle(stringResource(id = titleId))
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        actions = {
            IconButton(onClick = { navigateTo(Screen.Notifications) }) {
                Icon(
                    Icons.Outlined.Notifications,
                    contentDescription = stringResource(id = R.string.screen_notifications)
                )
            }
        }
    )
}

@Composable
fun TopBarBackButton(title: String, navigateUp: () -> Unit) {
    TopAppBar(
        title = { TopBarTitle(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.icon_back)
                )
            }
        },
    )
}

@Composable
private fun TopBarTitle(title: String) {
    Text(
        text = title,
        maxLines = 1
    )
}
