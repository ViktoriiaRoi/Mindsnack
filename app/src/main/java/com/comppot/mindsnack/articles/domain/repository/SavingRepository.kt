package com.comppot.mindsnack.articles.domain.repository

import com.comppot.mindsnack.articles.domain.model.Article

interface SavingRepository {
    suspend fun getSavedArticles(page: Int = 1): Result<List<Article>>
    suspend fun saveArticle(articleId: Long): Result<Int>
    suspend fun removeArticle(articleId: Long): Result<Int>
}
