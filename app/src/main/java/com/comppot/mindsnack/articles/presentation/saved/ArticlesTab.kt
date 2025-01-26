package com.comppot.mindsnack.articles.presentation.saved

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.comppot.mindsnack.R
import com.comppot.mindsnack.articles.domain.model.Article
import com.comppot.mindsnack.articles.presentation.components.SavedArticleItem
import com.comppot.mindsnack.core.presentation.components.AppendStateHandler
import com.comppot.mindsnack.core.presentation.components.PagingBox

@Composable
fun ArticlesTab(articles: LazyPagingItems<Article>, openArticle: (Long) -> Unit) {
    ArticlePagingList(articles, openArticle)
}

@Composable
private fun ArticlePagingList(articles: LazyPagingItems<Article>, onArticleClick: (Long) -> Unit) {
    PagingBox(articles, emptyMessage = stringResource(R.string.saved_screen_no_articles)) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
            modifier = Modifier.fillMaxSize()
        ) {
            items(articles.itemCount, key = articles.itemKey { it.id }) { index ->
                articles[index]?.let {
                    SavedArticleItem(
                        article = it,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onArticleClick
                    )
                }
            }
            item(span = StaggeredGridItemSpan.FullLine) {
                AppendStateHandler(articles)
            }
        }
    }
}
