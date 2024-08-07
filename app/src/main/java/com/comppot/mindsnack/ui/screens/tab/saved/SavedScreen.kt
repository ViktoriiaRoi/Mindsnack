package com.comppot.mindsnack.ui.screens.tab.saved

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comppot.mindsnack.R
import com.comppot.mindsnack.model.Article
import com.comppot.mindsnack.ui.components.FullScreenLoading
import com.comppot.mindsnack.ui.components.SavedArticleCard

@Composable
fun SavedScreen(openArticle: (Long) -> Unit = {}, viewModel: SavedViewModel = hiltViewModel()) {
    val state = viewModel.savedState.collectAsState().value

    when {
        state.isLoading -> FullScreenLoading()
        state.articles.isEmpty() -> NoArticles()
        else -> ArticleList(state.articles, openArticle)
    }
}

@Composable
private fun ArticleList(articles: List<Article>, openArticle: (Long) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(articles) {
            SavedArticleCard(it, Modifier.fillMaxWidth(), openArticle)
        }
    }
}

@Composable
private fun NoArticles() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            stringResource(id = R.string.saved_screen_no_articles),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
private fun SavedScreenPreview() {
    SavedScreen()
}
