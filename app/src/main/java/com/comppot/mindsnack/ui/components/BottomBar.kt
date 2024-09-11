package com.comppot.mindsnack.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.comppot.mindsnack.R
import com.comppot.mindsnack.ui.navigation.Screen

data class NavigationItem(
    val screen: Screen,
    @StringRes val label: Int,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector
)

@Composable
fun TabBottomBar(bottomNavController: NavHostController) {
    val navigationItems = listOf(
        NavigationItem(Screen.Tab.Home, R.string.screen_home, Icons.Filled.Home, Icons.Outlined.Home),
        NavigationItem(Screen.Tab.Search, R.string.screen_search, Icons.Filled.Search, Icons.Outlined.Search),
        NavigationItem(Screen.Tab.Saved, R.string.screen_saved, Icons.Filled.Bookmark, Icons.Outlined.BookmarkBorder),
        NavigationItem(Screen.Tab.Profile, R.string.screen_profile, Icons.Filled.Person, Icons.Outlined.Person)
    )

    NavigationBar(tonalElevation = 0.dp) {
        val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navigationItems.forEach { item ->
            val isSelected = currentRoute == item.screen.route
            NavigationBarItem(
                icon = {
                    Icon(
                        if (isSelected) item.filledIcon else item.outlinedIcon,
                        contentDescription = stringResource(id = item.label),
                    )
                },
                label = { Text(stringResource(id = item.label)) },
                selected = isSelected,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                        LocalAbsoluteTonalElevation.current
                    )
                ),
                onClick = {
                    bottomNavController.navigate(item.screen.route) {
                        popUpTo(bottomNavController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun ArticleBottomBar(
    isSaved: Boolean,
    savedCount: Int,
    onSavedClick: (Boolean) -> Unit = {},
    onShareClick: () -> Unit = {},
    navigateUp: () -> Unit = {}
) {
    var isLoading by remember { mutableStateOf(false) }

    NavigationBar(tonalElevation = 0.dp) {
        BottomToolbarItem(enabled = !isLoading, onClick = {
            isLoading = true
            navigateUp()
        }) {
            Icon(
                Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.icon_back)
            )
        }
        BottomToolbarItem(enabled = !isLoading, onClick = { onSavedClick(!isSaved) }) {
            SaveArticleIcon(isSaved, savedCount)
        }
        BottomToolbarItem(enabled = !isLoading, onClick = onShareClick) {
            Icon(
                Icons.Outlined.Share,
                contentDescription = stringResource(id = R.string.icon_share),
            )
        }
    }
}

@Composable
private fun SaveArticleIcon(isSaved: Boolean, savedCount: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            if (isSaved) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
            contentDescription = stringResource(id = R.string.icon_save)
        )
        Text("$savedCount", style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
private fun RowScope.BottomToolbarItem(
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    NavigationBarItem(
        selected = false,
        onClick = onClick,
        enabled = enabled,
        icon = content
    )
}

@Preview
@Composable
private fun TabBottomBarPreview() {
    val navController = rememberNavController()
    TabBottomBar(navController)
}

@Preview
@Composable
private fun ArticleBottomBarPreview() {
    ArticleBottomBar(false, 0)
}
