package com.comppot.mindsnack.articles.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.comppot.mindsnack.R
import com.comppot.mindsnack.articles.domain.model.Article
import com.comppot.mindsnack.articles.domain.model.Category
import com.comppot.mindsnack.articles.presentation.components.ArticleItem
import com.comppot.mindsnack.articles.presentation.components.CategoryItem
import com.comppot.mindsnack.core.presentation.components.AppendStateHandler
import com.comppot.mindsnack.core.presentation.components.PagingBox
import com.comppot.mindsnack.core.presentation.components.RefreshStateHandler
import com.comppot.mindsnack.core.presentation.components.isEmpty

@Composable
fun HomeScreen(openArticle: (Long) -> Unit = {}, viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.homeState.collectAsState()
    val articles = viewModel.articlesState.collectAsLazyPagingItems()

    Column(modifier = Modifier.fillMaxSize()) {
        if (state.categories.isNotEmpty()) {
            CategoryList(state.categories, state.selectedCategory, viewModel::selectCategory)
        }
        ArticlePagingList(articles, openArticle)
    }
}

@Composable
private fun CategoryList(
    categories: List<Category>,
    selectedCategory: Category,
    onSelectCategory: (Category) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) {
            CategoryItem(it, isSelected = selectedCategory == it) { onSelectCategory(it) }
        }
    }
}

@Composable
private fun ArticlePagingList(articles: LazyPagingItems<Article>, onArticleClick: (Long) -> Unit) {
    PagingBox(articles) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(articles.itemCount, key = articles.itemKey { it.id }) { index ->
                articles[index]?.let {
                    ArticleItem(it, Modifier.fillMaxWidth(), onArticleClick)
                }
            }
            item { AppendStateHandler(articles.loadState.append) }
        }
        RefreshStateHandler(
            articles.loadState.refresh,
            isEmpty = articles.isEmpty(),
            emptyMessage = stringResource(R.string.home_screen_no_articles)
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
