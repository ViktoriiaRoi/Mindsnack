package com.comppot.mindsnack.ui.screens.article

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.comppot.mindsnack.ui.components.ArticleBottomBar

@Composable
fun ArticleScreen(articleId: Long, navigateUp: () -> Unit) {
    Scaffold(
        bottomBar = { ArticleBottomBar(navigateUp) },
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text("Article #$articleId")
        }
    }
}
