package com.comppot.mindsnack.articles.domain.model

data class ArticleDetails(
    val id: Long,
    val image: String,
    val title: String,
    val postDate: Long,
    val readTime: Int,
    val categoryId: Int,
    val numberOfCards: Int = 0,
    val savedCount: Int = 0,
    val cards: List<CardData> = listOf()
)
