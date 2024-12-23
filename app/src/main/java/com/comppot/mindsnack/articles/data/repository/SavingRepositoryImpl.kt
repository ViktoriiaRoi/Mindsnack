package com.comppot.mindsnack.articles.data.repository

import com.comppot.mindsnack.articles.data.remote.SavingApi
import com.comppot.mindsnack.articles.data.remote.dto.toArticle
import com.comppot.mindsnack.articles.domain.model.Article
import com.comppot.mindsnack.articles.domain.repository.SavingRepository
import com.comppot.mindsnack.core.data.utils.runSafe
import javax.inject.Inject

class SavingRepositoryImpl @Inject constructor(private val api: SavingApi) : SavingRepository {
    override suspend fun getSavedArticles(page: Int): Result<List<Article>> {
        return runSafe { api.getSavedArticles(page) }.map {
            it.objects.map { articleDTO -> articleDTO.toArticle() }
        }
    }

    override suspend fun saveArticle(articleId: Long): Result<Int> {
        return runSafe { api.saveArticle(articleId) }.map { it.savedCount }
    }

    override suspend fun removeArticle(articleId: Long): Result<Int> {
        return runSafe { api.removeArticle(articleId) }.map { it.savedCount }
    }
}
