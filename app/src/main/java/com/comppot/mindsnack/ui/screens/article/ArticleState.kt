package com.comppot.mindsnack.ui.screens.article

import com.comppot.mindsnack.model.ArticleDetails

data class ArticleState(
    val isLoading: Boolean = true,
    val articleDetails: ArticleDetails? = null,
)
