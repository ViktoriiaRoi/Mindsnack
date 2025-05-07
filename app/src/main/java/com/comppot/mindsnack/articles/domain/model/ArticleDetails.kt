package com.comppot.mindsnack.articles.domain.model

import com.google.gson.annotations.SerializedName

data class ArticleDetails(
    @SerializedName("id")
    val id: Long,
    @SerializedName("image")
    val image: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("postDate")
    val postDate: Long,
    @SerializedName("numberOfCards")
    val numberOfCards: Int = 0,
    @SerializedName("savedCount")
    val savedCount: Int = 0,
    @SerializedName("isSaved")
    val isSaved: Boolean = false,
    @SerializedName("cards")
    val cards: List<CardData> = listOf()
) {
    fun updateCard(cardId: Long, transform: (CardData) -> CardData): ArticleDetails {
        val updatedCards = cards.toMutableList().apply {
            val index = indexOfFirst { it.id == cardId }
            if (index != -1) this[index] = transform(this[index])
        }
        return copy(cards = updatedCards)
    }
}
