package com.comppot.mindsnack.articles.data.remote.dto

import com.comppot.mindsnack.articles.domain.model.CardData

data class CardDTO(
    val id: Long,
    val title: String,
    val text: String,
    val isSaved: Boolean?
)

fun CardDTO.toCardData(defaultSaved: Boolean) = CardData(
    id = id,
    title = title,
    text = text,
    isSaved = isSaved ?: defaultSaved
)
