package com.comppot.mindsnack.ui.screens.article

import com.comppot.mindsnack.model.ArticleDetails

data class ArticleState(
    val isLoading: Boolean = true,
    val articleDetails: ArticleDetails? = null,
    val isSaved: Boolean = false,
    val isRatingShown: Boolean = true
) {
    val savedCount get() = articleDetails?.let { it.savedCount + if (isSaved) 1 else 0 } ?: 0
}
