package com.comppot.mindsnack.ui.screens.notifications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.comppot.mindsnack.R
import com.comppot.mindsnack.ui.components.TopBarBackButton

@Composable
fun NotificationsScreen(navigateUp: () -> Unit) {
    val screenTitle = stringResource(id = R.string.screen_notifications)

    Scaffold(
        topBar = { TopBarBackButton(screenTitle, navigateUp) },
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text("Notifications Screen")
        }
    }
}
