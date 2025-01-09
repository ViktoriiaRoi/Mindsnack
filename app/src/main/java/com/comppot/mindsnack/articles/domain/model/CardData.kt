package com.comppot.mindsnack.articles.domain.model

data class CardData(
    val id: Long,
    val title: String?,
    val text: String,
    val isSaved: Boolean = false
)
