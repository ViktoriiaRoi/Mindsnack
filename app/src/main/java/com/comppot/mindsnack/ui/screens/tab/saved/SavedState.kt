package com.comppot.mindsnack.ui.screens.tab.saved

import com.comppot.mindsnack.model.Article

data class SavedState(
    val isLoading: Boolean = true,
    val articles: List<Article> = emptyList(),
)