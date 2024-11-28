package com.comppot.mindsnack.ui.screens.tab.saved

import androidx.lifecycle.ViewModel
import com.comppot.mindsnack.data.article.ArticleRepository
import com.comppot.mindsnack.data.settings.SettingsRepository
import com.comppot.mindsnack.model.Article
import com.comppot.mindsnack.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val savedArticles: Flow<List<Article>> = settingsRepository.savedArticleIds.map {
        getArticlesFromIds(it)
    }

    private suspend fun getArticlesFromIds(ids: List<Long>): List<Article> {
        val articles = articleRepository.getRecommendations(Category.ALL).getOrNull()
        return ids.mapNotNull { id ->
            articles?.find { it.id == id }
        }
    }
}
