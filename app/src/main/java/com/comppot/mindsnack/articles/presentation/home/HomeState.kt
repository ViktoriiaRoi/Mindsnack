package com.comppot.mindsnack.articles.presentation.home

import com.comppot.mindsnack.articles.domain.model.Category

data class HomeState(
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category = Category.ALL,
)
