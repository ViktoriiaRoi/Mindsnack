package com.comppot.mindsnack.articles.presentation.article

import com.comppot.mindsnack.articles.domain.model.ArticleDetails
import com.comppot.mindsnack.core.presentation.Status

data class ArticleState(
    val detailsStatus: Status<ArticleDetails> = Status.Loading,
    val isSaved: Boolean = false,
    val savedCount: Int = 0,
    val isRatingShown: Boolean = true
)
