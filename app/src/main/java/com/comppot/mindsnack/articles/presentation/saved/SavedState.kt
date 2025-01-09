package com.comppot.mindsnack.articles.presentation.saved

import com.comppot.mindsnack.articles.domain.model.Article
import com.comppot.mindsnack.articles.domain.model.SavedCard
import com.comppot.mindsnack.core.presentation.Status

data class SavedState(
    val articlesStatus: Status<List<Article>> = Status.Loading,
    val cardsStatus: Status<List<SavedCard>> = Status.Loading,
)
