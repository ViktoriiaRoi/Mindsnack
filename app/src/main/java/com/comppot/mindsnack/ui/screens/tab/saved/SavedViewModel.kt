package com.comppot.mindsnack.ui.screens.tab.saved

import androidx.lifecycle.ViewModel
import com.comppot.mindsnack.data.article.ArticleRepository
import com.comppot.mindsnack.data.settings.SettingsRepository
import com.comppot.mindsnack.data.common.CustomException
import com.comppot.mindsnack.model.Article
import com.comppot.mindsnack.model.Category
import com.comppot.mindsnack.ui.common.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val articlesStatus: Flow<Status<List<Article>>> = settingsRepository.savedArticleIds.map {
        getArticlesFromIds(it)
    }

    private suspend fun getArticlesFromIds(ids: List<Long>): Status<List<Article>> {
        val result = articleRepository.getRecommendations(Category.ALL)
        val status = result.fold(
            onSuccess = { articles ->
                val list = filterArticles(articles, ids)
                if (list.isEmpty()) Status.Empty else Status.Success(list)
            }, onFailure = { error -> Status.Error(error as CustomException) }
        )
        return status
    }

    private fun filterArticles(articles: List<Article>, ids: List<Long>): List<Article> {
        return ids.mapNotNull { id ->
            articles.find { it.id == id }
        }
    }
}
