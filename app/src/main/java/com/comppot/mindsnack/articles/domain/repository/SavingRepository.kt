package com.comppot.mindsnack.articles.domain.repository

import androidx.paging.PagingData
import com.comppot.mindsnack.articles.domain.model.Article
import com.comppot.mindsnack.articles.domain.model.SavedCard
import kotlinx.coroutines.flow.Flow

interface SavingRepository {
    suspend fun getSavedArticles(): Flow<PagingData<Article>>
    suspend fun saveArticle(articleId: Long): Result<Int>
    suspend fun removeArticle(articleId: Long): Result<Int>
    suspend fun getSavedCards(): Flow<PagingData<SavedCard>>
    suspend fun saveCard(cardId: Long): Result<Int>
    suspend fun removeCard(cardId: Long): Result<Int>
}
