package com.comppot.mindsnack.ui.screens.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.data.article.ArticleRepository
import com.comppot.mindsnack.data.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _articleState = MutableStateFlow(ArticleState())
    val articleState = _articleState.asStateFlow()

    private val articleId: Long? get() = _articleState.value.articleDetails?.id

    fun fetchArticleDetails(id: Long) = viewModelScope.launch {
        val articleDetails = articleRepository.getArticleById(id)
        _articleState.emit(
            ArticleState(
                isLoading = false,
                articleDetails = articleDetails,
                isSaved = isArticleSaved(id)
            )
        )
    }

    fun updateSave(isSaved: Boolean) = articleId?.let { id ->
        viewModelScope.launch {
            if (isSaved) {
                settingsRepository.saveArticle(id)
            } else {
                settingsRepository.removeArticle(id)
            }
            _articleState.emit(
                _articleState.value.copy(
                    isSaved = isSaved,
                )
            )
        }
    }

    private suspend fun isArticleSaved(id: Long): Boolean {
        val savedList = settingsRepository.savedArticleIds.first()
        return savedList.contains(id)
    }
}
