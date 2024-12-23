package com.comppot.mindsnack.articles.presentation.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.R
import com.comppot.mindsnack.core.data.settings.SettingsRepository
import com.comppot.mindsnack.core.common.CustomException
import com.comppot.mindsnack.articles.domain.model.Rating
import com.comppot.mindsnack.articles.domain.repository.ArticleRepository
import com.comppot.mindsnack.core.presentation.Status
import com.comppot.mindsnack.core.presentation.utils.SnackbarController
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

    private var articleId: Long? = null

    fun fetchArticleDetails(id: Long) = viewModelScope.launch {
        val result = articleRepository.getArticleDetails(id)
        val status = result.fold(
            onSuccess = {
                articleId = id
                Status.Success(it)
            },
            onFailure = { error -> Status.Error(error as CustomException) }
        )
        _articleState.emit(
            ArticleState(
                detailsStatus = status,
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
            _articleState.update { it.copy(isSaved = isSaved) }
        }
    }

    fun shareArticle() = viewModelScope.launch {
        SnackbarController.showMessage(R.string.snackbar_not_implemented)
    }

    fun saveRating(rating: Rating) = articleId?.let { id ->
        viewModelScope.launch {
            articleRepository.postRating(id, rating.value)
                .onSuccess {
                    _articleState.update { it.copy(isRatingShown = false) }
                    SnackbarController.showMessage(R.string.article_rating_success)
                }.onFailure {
                    SnackbarController.showErrorMessage(it)
                }
        }
    }

    private suspend fun isArticleSaved(id: Long): Boolean {
        val savedList = settingsRepository.savedArticleIds.first()
        return savedList.contains(id)
    }
}
