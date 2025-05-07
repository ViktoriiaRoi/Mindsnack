package com.comppot.mindsnack.articles.data.remote.dto

import com.comppot.mindsnack.articles.domain.model.Article

data class ArticleDTO(
    val id: Long,
    val image: String,
    val title: String,
    val postDate: Long,
    val numberOfCards: Int
)

fun ArticleDTO.toArticle() = Article(
    id = id,
    image = image,
    title = title,
    postDate = postDate,
    numberOfCards = numberOfCards
)
