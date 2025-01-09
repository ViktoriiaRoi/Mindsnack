package com.comppot.mindsnack.articles.domain.model

data class ArticleDetails(
    val id: Long,
    val image: String,
    val title: String,
    val postDate: Long,
    val numberOfCards: Int = 0,
    val savedCount: Int = 0,
    val isSaved: Boolean = false,
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
