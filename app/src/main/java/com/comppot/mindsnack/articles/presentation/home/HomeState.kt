package com.comppot.mindsnack.articles.presentation.home

import com.comppot.mindsnack.articles.domain.model.Article
import com.comppot.mindsnack.articles.domain.model.Category
import com.comppot.mindsnack.core.presentation.Status

data class HomeState(
    val articlesStatus: Status<List<Article>> = Status.Loading,
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category = Category.ALL,
)