package com.comppot.mindsnack.ui.screens.tab.saved

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comppot.mindsnack.R
import com.comppot.mindsnack.model.Article
import com.comppot.mindsnack.ui.common.Status
import com.comppot.mindsnack.ui.components.SavedArticleItem
import com.comppot.mindsnack.ui.components.StatusHandler

@Composable
fun SavedScreen(openArticle: (Long) -> Unit = {}, viewModel: SavedViewModel = hiltViewModel()) {
    val status by viewModel.articlesStatus.collectAsState(Status.Loading)

    StatusHandler(
        status = status,
        emptyMessage = stringResource(R.string.saved_screen_no_articles)
    ) { articles ->
        ArticleList(articles, openArticle)
    }
}

@Composable
private fun ArticleList(articles: List<Article>, openArticle: (Long) -> Unit) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp,
        modifier = Modifier.fillMaxSize()
    ) {
        items(articles) {
            SavedArticleItem(it, Modifier.fillMaxWidth(), openArticle)
        }
    }
}

@Preview
@Composable
private fun SavedScreenPreview() {
    SavedScreen()
}
