package com.comppot.mindsnack.articles.presentation.article

import com.comppot.mindsnack.articles.domain.model.ArticleDetails
import com.comppot.mindsnack.core.common.CustomException

data class ArticleState(
    val details: ArticleDetails? = null,
    val error: CustomException? = null,
    val isRatingShown: Boolean = true
)
