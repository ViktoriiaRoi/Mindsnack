package com.comppot.mindsnack.articles.data.repository

import com.comppot.mindsnack.articles.domain.repository.ArticleRepository
import com.comppot.mindsnack.core.data.api.MindSnackApi
import com.comppot.mindsnack.core.data.utils.runSafe
import com.comppot.mindsnack.articles.domain.model.Article
import com.comppot.mindsnack.articles.domain.model.ArticleDetails
import com.comppot.mindsnack.articles.domain.model.Category
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(private val api: MindSnackApi) : ArticleRepository {
    override suspend fun getRecommendations(category: Category): Result<List<Article>> {
        return runSafe { api.getRecommendations(page = 1) }.map { it.articles }
    }

    override suspend fun searchArticles(query: String): Result<List<Article>> {
        return runSafe { api.searchArticles(query) }
    }

    override suspend fun getArticleDetails(id: Long): Result<ArticleDetails> {
        return runSafe { api.getArticleDetails(id) }
    }

    override suspend fun getCategories(): Result<List<Category>> {
        return runSafe { api.getCategories() }
    }
}
