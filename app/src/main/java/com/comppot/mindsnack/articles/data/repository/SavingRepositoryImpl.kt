package com.comppot.mindsnack.articles.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.comppot.mindsnack.articles.data.paging.SavedArticlesPagingSource
import com.comppot.mindsnack.articles.data.paging.SavedCardsPagingSource
import com.comppot.mindsnack.articles.data.remote.SavingApi
import com.comppot.mindsnack.articles.domain.model.Article
import com.comppot.mindsnack.articles.domain.model.SavedCard
import com.comppot.mindsnack.articles.domain.repository.SavingRepository
import com.comppot.mindsnack.core.data.utils.runSafe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavingRepositoryImpl @Inject constructor(
    private val api: SavingApi,
    private val pagingConfig: PagingConfig
) : SavingRepository {

    override suspend fun getSavedArticles(): Flow<PagingData<Article>> {
        return Pager(pagingConfig) {
            SavedArticlesPagingSource(api)
        }.flow
    }

    override suspend fun saveArticle(articleId: Long): Result<Int> {
        return runSafe { api.saveArticle(articleId) }.map { it.savedCount }
    }

    override suspend fun removeArticle(articleId: Long): Result<Int> {
        return runSafe { api.removeArticle(articleId) }.map { it.savedCount }
    }

    override suspend fun getSavedCards(): Flow<PagingData<SavedCard>> {
        return Pager(pagingConfig) {
            SavedCardsPagingSource(api)
        }.flow
    }

    override suspend fun saveCard(cardId: Long): Result<Int> {
        return runSafe { api.saveCard(cardId) }.map { it.savedCount }
    }

    override suspend fun removeCard(cardId: Long): Result<Int> {
        return runSafe { api.removeCard(cardId) }.map { it.savedCount }
    }
}
