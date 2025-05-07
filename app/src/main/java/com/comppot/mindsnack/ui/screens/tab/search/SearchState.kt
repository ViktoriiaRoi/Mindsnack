package com.comppot.mindsnack.ui.screens.tab.search

import com.comppot.mindsnack.model.Article

data class SearchState (
    val isLoading: Boolean = true,
    val articles: List<Article> = emptyList(),
)