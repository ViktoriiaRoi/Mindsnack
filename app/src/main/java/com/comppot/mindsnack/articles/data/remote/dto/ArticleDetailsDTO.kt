package com.comppot.mindsnack.articles.data.remote.dto

import com.comppot.mindsnack.articles.domain.model.ArticleDetails

data class ArticleDetailsDTO(
    val id: Long,
    val image: String,
    val title: String,
    val postDate: Long,
    val numberOfCards: Int,
    val savedCount: Int,
    val isSaved: Boolean,
    val cards: List<CardDTO> = listOf()
)

fun ArticleDetailsDTO.toArticleDetails() = ArticleDetails(
    id = id,
    image = image,
    title = title,
    postDate = postDate,
    numberOfCards = numberOfCards,
    savedCount = savedCount,
    isSaved = isSaved,
    cards = cards.map { it.toCardData() }
)
