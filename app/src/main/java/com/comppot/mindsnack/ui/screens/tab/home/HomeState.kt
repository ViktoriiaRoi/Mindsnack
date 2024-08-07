package com.comppot.mindsnack.ui.screens.tab.home

import com.comppot.mindsnack.model.Article
import com.comppot.mindsnack.model.Category

data class HomeState (
    val isLoading: Boolean = true,
    val categories: List<Category> = listOf(Category.ALL),
    val articles: List<Article> = emptyList(),
    val selectedCategory: Category = Category.ALL,
)