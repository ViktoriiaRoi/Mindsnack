package com.comppot.mindsnack.articles.domain.repository

import com.comppot.mindsnack.articles.domain.model.Article
import com.comppot.mindsnack.articles.domain.model.SavedCard

interface SavingRepository {
    suspend fun getSavedArticles(page: Int = 1): Result<List<Article>>
    suspend fun saveArticle(articleId: Long): Result<Int>
    suspend fun removeArticle(articleId: Long): Result<Int>
    suspend fun getSavedCards(page: Int = 1): Result<List<SavedCard>>
    suspend fun saveCard(cardId: Long): Result<Int>
    suspend fun removeCard(cardId: Long): Result<Int>
}
