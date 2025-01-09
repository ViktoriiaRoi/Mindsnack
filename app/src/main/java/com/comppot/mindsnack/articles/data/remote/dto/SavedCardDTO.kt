package com.comppot.mindsnack.articles.data.remote.dto

import com.comppot.mindsnack.articles.domain.model.SavedCard

data class SavedCardDTO(
    val articleId: Long,
    val details: CardDTO
)

fun SavedCardDTO.toSavedCard() = SavedCard(
    articleId = articleId,
    details = details.toCardData(defaultSaved = true)
)
