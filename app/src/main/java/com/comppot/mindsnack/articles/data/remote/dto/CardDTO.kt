package com.comppot.mindsnack.articles.data.remote.dto

import com.comppot.mindsnack.articles.domain.model.CardData

data class CardDTO(
    val id: Long,
    val title: String,
    val text: String
)

fun CardDTO.toCardData() = CardData(
    id = id,
    title = title,
    text = text
)
