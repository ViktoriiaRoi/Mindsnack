package com.comppot.mindsnack.ui.screens.tab.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.comppot.mindsnack.model.Category
import com.comppot.mindsnack.ui.components.ArticleCard
import com.comppot.mindsnack.ui.components.CategoryItem
import com.comppot.mindsnack.ui.components.FullScreenLoading

@Composable
fun HomeScreen(openArticle: (Long) -> Unit = {}, viewModel: HomeViewModel = hiltViewModel()) {
    val state = viewModel.homeState.collectAsState().value

    Column {
        CategoryList(state.categories, state.selectedCategory, viewModel::selectCategory)
        when {
            state.isLoading -> FullScreenLoading()
            state.articles.isEmpty() -> NoArticles()
            else -> ArticleList(state.articles, openArticle)
        }
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
private fun ArticleList(articles: List<Article>, openArticle: (Long) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(articles) {
            ArticleCard(it, Modifier.fillMaxWidth(), openArticle)
        }
    }
}

@Composable
private fun NoArticles() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            stringResource(id = R.string.home_screen_no_articles),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
