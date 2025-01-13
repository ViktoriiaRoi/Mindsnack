@file:OptIn(ExperimentalMaterial3Api::class)

package com.comppot.mindsnack.articles.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.comppot.mindsnack.articles.presentation.components.ArticleItem
import com.comppot.mindsnack.articles.presentation.components.CustomSearchBar
import com.comppot.mindsnack.core.presentation.components.AppendStateHandler
import com.comppot.mindsnack.core.presentation.components.PagingBox
import com.comppot.mindsnack.core.presentation.components.RefreshStateHandler
import com.comppot.mindsnack.core.presentation.components.SuggestBookDialog
import com.comppot.mindsnack.core.presentation.components.isEmpty

@Composable
fun SearchScreen(openArticle: (Long) -> Unit = {}, viewModel: SearchViewModel = hiltViewModel()) {
    val articles = viewModel.articlesState.collectAsLazyPagingItems()
    var isDialogShown by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ArticleSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onSearch = viewModel::searchArticles,
            showSuggestion = articles.isEmpty(),
            onSuggestClick = { isDialogShown = true }
        )
        ArticlePagingList(articles, openArticle)
    }

    if (isDialogShown) {
        SuggestBookDialog(
            onDismiss = { isDialogShown = false },
            onSave = { title, author -> viewModel.suggestBook(title, author) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleSearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {},
    showSuggestion: Boolean = false,
    onSuggestClick: () -> Unit = {}
) {
    CustomSearchBar(
        onSearch = onSearch,
        expanded = showSuggestion,
        modifier = modifier,
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface,
            dividerColor = MaterialTheme.colorScheme.outline
        )
    ) {
        BookSuggestion(onSuggestClick)
    }
}

@Composable
private fun BookSuggestion(onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AddIcon(onClick)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                stringResource(id = R.string.search_screen_suggestion_title),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                stringResource(id = R.string.search_screen_suggestion_description),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun AddIcon(onClick: () -> Unit) {
    FilledIconButton(
        onClick = onClick,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.icon_add)
        )
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
            emptyMessage = stringResource(R.string.search_screen_no_articles)
        )
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}
