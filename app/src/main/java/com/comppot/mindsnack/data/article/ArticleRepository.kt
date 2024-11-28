package com.comppot.mindsnack.data.article

import com.comppot.mindsnack.data.api.MindSnackApi
import com.comppot.mindsnack.data.utils.runSafe
import com.comppot.mindsnack.model.Article
import com.comppot.mindsnack.model.ArticleDetails
import com.comppot.mindsnack.model.Category
import javax.inject.Inject

interface ArticleRepository {
    suspend fun getRecommendations(category: Category): Result<List<Article>>
    suspend fun searchArticles(query: String): Result<List<Article>>
    suspend fun getArticleDetails(id: Long): Result<ArticleDetails>
    suspend fun getCategories(): Result<List<Category>>
}

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
