package com.comppot.mindsnack.articles.domain.repository

import androidx.paging.PagingData
import com.comppot.mindsnack.articles.domain.model.Article
import com.comppot.mindsnack.articles.domain.model.ArticleDetails
import com.comppot.mindsnack.articles.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    suspend fun getCategories(): Result<List<Category>>
    suspend fun getRecommendations(category: Category): Flow<PagingData<Article>>
    suspend fun searchArticles(query: String): Flow<PagingData<Article>>
    suspend fun getArticleDetails(id: Long): Result<ArticleDetails>
    suspend fun readArticle(articleId: Long)
    suspend fun postRating(articleId: Long, score: Int): Result<Unit>
    suspend fun suggestArticle(title: String, author: String): Result<Unit>
}
