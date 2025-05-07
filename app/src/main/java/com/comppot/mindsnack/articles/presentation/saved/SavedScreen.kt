package com.comppot.mindsnack.articles.presentation.saved

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.comppot.mindsnack.R

enum class SavedScreenTab(@StringRes val title: Int) {
    CARDS(R.string.saved_screen_tab_cards),
    ARTICLES(R.string.saved_screen_tab_articles)
}

@Composable
fun SavedScreen(openArticle: (Long) -> Unit = {}, viewModel: SavedViewModel = hiltViewModel()) {
    var selectedTab by remember { mutableStateOf(SavedScreenTab.CARDS) }

    val cards = viewModel.cardsState.collectAsLazyPagingItems()
    val articles = viewModel.articlesState.collectAsLazyPagingItems()

    Column {
        SavedTabRow(selectedTab) { selectedTab = it }
        when (selectedTab) {
            SavedScreenTab.CARDS -> CardsTab(cards, openArticle)
            SavedScreenTab.ARTICLES -> ArticlesTab(articles, openArticle)
        }
    }
}

@Composable
private fun SavedTabRow(selectedTab: SavedScreenTab, onSelectTab: (SavedScreenTab) -> Unit = {}) {
    TabRow(selectedTabIndex = selectedTab.ordinal, containerColor = Color.Transparent) {
        SavedScreenTab.entries.forEach {
            Tab(
                text = { Text(stringResource(it.title)) },
                selected = selectedTab == it,
                onClick = { onSelectTab(it) }
            )
        }
    }
}

@Preview
@Composable
private fun SavedScreenPreview() {
    SavedScreen()
}
