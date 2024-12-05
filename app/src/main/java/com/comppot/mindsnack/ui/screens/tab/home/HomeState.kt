package com.comppot.mindsnack.ui.screens.tab.home

import com.comppot.mindsnack.model.Article
import com.comppot.mindsnack.model.Category
import com.comppot.mindsnack.ui.common.Status

data class HomeState(
    val articlesStatus: Status<List<Article>> = Status.Loading,
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category = Category.ALL,
)