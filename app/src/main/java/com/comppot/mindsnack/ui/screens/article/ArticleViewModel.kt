package com.comppot.mindsnack.ui.screens.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.R
import com.comppot.mindsnack.data.article.ArticleRepository
import com.comppot.mindsnack.data.settings.SettingsRepository
import com.comppot.mindsnack.model.Rating
import com.comppot.mindsnack.ui.utils.SnackbarController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
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

    fun updateArticleSaved(isSaved: Boolean) = articleId?.let { id ->
        viewModelScope.launch {
            if (isSaved) {
                settingsRepository.saveArticle(id)
            } else {
                settingsRepository.removeArticle(id)
            }
            _articleState.update { state ->
                state.copy(isSaved = isSaved)
            }
        }
    }

    fun shareArticle() = viewModelScope.launch {
        SnackbarController.showMessage(R.string.snackbar_not_implemented)
    }

    fun saveRating(rating: Rating) = viewModelScope.launch {
        SnackbarController.showMessage(R.string.article_rating_success)
        _articleState.update { state ->
            state.copy(isRatingShown = false)
        }
    }

    private suspend fun isArticleSaved(id: Long): Boolean {
        val savedList = settingsRepository.savedArticleIds.first()
        return savedList.contains(id)
    }
}
