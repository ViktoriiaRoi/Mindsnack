package com.comppot.mindsnack.articles.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.comppot.mindsnack.articles.data.paging.RecommendationsPagingSource
import com.comppot.mindsnack.articles.data.paging.SearchPagingSource
import com.comppot.mindsnack.articles.data.remote.ArticleApi
import com.comppot.mindsnack.articles.data.remote.dto.toArticleDetails
import com.comppot.mindsnack.articles.data.remote.dto.toCategory
import com.comppot.mindsnack.articles.domain.model.Article
import com.comppot.mindsnack.articles.domain.model.ArticleDetails
import com.comppot.mindsnack.articles.domain.model.Category
import com.comppot.mindsnack.articles.domain.repository.ArticleRepository
import com.comppot.mindsnack.core.data.utils.runSafe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val api: ArticleApi,
    private val pagingConfig: PagingConfig
) : ArticleRepository {

    override suspend fun getCategories(): Result<List<Category>> {
        return runSafe { api.getCategories() }.map {
            it.map { categoryDTO -> categoryDTO.toCategory() }
        }
    }

    override fun getRecommendations(category: Category): Flow<PagingData<Article>> {
        return Pager(pagingConfig) {
            RecommendationsPagingSource(api, category)
        }.flow
    }

    override fun searchArticles(query: String): Flow<PagingData<Article>> {
        return Pager(pagingConfig) {
            SearchPagingSource(api, query)
        }.flow
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
