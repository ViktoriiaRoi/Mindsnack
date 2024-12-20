package com.comppot.mindsnack.articles.domain.repository

import com.comppot.mindsnack.articles.domain.model.Article
import com.comppot.mindsnack.articles.domain.model.ArticleDetails
import com.comppot.mindsnack.articles.domain.model.Category

interface ArticleRepository {
    suspend fun getRecommendations(category: Category): Result<List<Article>>
    suspend fun searchArticles(query: String): Result<List<Article>>
    suspend fun getArticleDetails(id: Long): Result<ArticleDetails>
    suspend fun getCategories(): Result<List<Category>>
}