package com.comppot.mindsnack.articles.data.repository

import com.comppot.mindsnack.articles.data.remote.ArticleApi
import com.comppot.mindsnack.articles.data.remote.dto.toArticle
import com.comppot.mindsnack.articles.data.remote.dto.toArticleDetails
import com.comppot.mindsnack.articles.data.remote.dto.toCategory
import com.comppot.mindsnack.articles.domain.model.Article
import com.comppot.mindsnack.articles.domain.model.ArticleDetails
import com.comppot.mindsnack.articles.domain.model.Category
import com.comppot.mindsnack.articles.domain.repository.ArticleRepository
import com.comppot.mindsnack.core.data.utils.runSafe
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(private val api: ArticleApi) : ArticleRepository {
    override suspend fun getCategories(): Result<List<Category>> {
        return runSafe { api.getCategories() }.map {
            it.map { categoryDTO -> categoryDTO.toCategory() }
        }
    }

    override suspend fun getRecommendations(category: Category, page: Int): Result<List<Article>> {
        return runSafe { api.getRecommendations(category.id, page) }.map {
            it.objects.map { articleDTO -> articleDTO.toArticle() }
        }
    }

    override suspend fun searchArticles(query: String, page: Int): Result<List<Article>> {
        return runSafe { api.searchArticles(query, page) }.map {
            it.objects.map { articleDTO -> articleDTO.toArticle() }
        }
    }

    override suspend fun getArticleDetails(id: Long): Result<ArticleDetails> {
        return runSafe { api.getArticleDetails(id) }
            .map { articleDetailsDTO -> articleDetailsDTO.toArticleDetails() }
    }

    override suspend fun readArticle(articleId: Long) {
        runSafe { api.readArticle(articleId) }
    }

    override suspend fun postRating(articleId: Long, score: Int): Result<Unit> {
        return runSafe { api.postRating(articleId, score) }
    }

    override suspend fun suggestArticle(title: String, author: String): Result<Unit> {
        return runSafe { api.suggestArticle(title, author) }
    }
}
