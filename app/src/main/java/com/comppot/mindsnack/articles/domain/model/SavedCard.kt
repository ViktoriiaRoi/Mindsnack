package com.comppot.mindsnack.articles.domain.model

import com.google.gson.annotations.SerializedName

data class SavedCard(
    @SerializedName("articleId")
    val articleId: Long,
    @SerializedName("details")
    val details: CardData
)
