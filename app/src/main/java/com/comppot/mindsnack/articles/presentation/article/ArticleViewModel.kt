package com.comppot.mindsnack.articles.presentation.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.R
import com.comppot.mindsnack.articles.domain.model.ArticleDetails
import com.comppot.mindsnack.articles.domain.model.Rating
import com.comppot.mindsnack.articles.domain.repository.ArticleRepository
import com.comppot.mindsnack.articles.domain.repository.SavingRepository
import com.comppot.mindsnack.core.common.CustomException
import com.comppot.mindsnack.core.presentation.utils.SnackbarController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val savingRepository: SavingRepository
) : ViewModel() {

    private val _articleState = MutableStateFlow(ArticleState())
    val articleState = _articleState.asStateFlow()

    private var articleId: Long? = null

    fun fetchArticleDetails(id: Long) = viewModelScope.launch {
        articleRepository.getArticleDetails(id)
            .onSuccess { details ->
                articleId = id
                _articleState.emit(
                    ArticleState(details = details)
                )
            }.onFailure { throwable ->
                _articleState.emit(
                    ArticleState(error = throwable as CustomException)
                )
            }
    }

    fun updateArticleSaved(isSaved: Boolean) = articleId?.let { id ->
        viewModelScope.launch {
            handleSaving(
                isSaved = isSaved,
                saveAction = { savingRepository.saveArticle(id) },
                removeAction = { savingRepository.removeArticle(id) },
            ) { savedCount ->
                updateArticleState {
                    it.copy(isSaved = isSaved, savedCount = savedCount)
                }
            }
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

    fun readArticle() = articleId?.let { id ->
        viewModelScope.launch {
            articleRepository.readArticle(id)
        }
    }

    fun updateCardSaved(cardId: Long, isSaved: Boolean) = viewModelScope.launch {
        handleSaving(
            isSaved = isSaved,
            saveAction = { savingRepository.saveCard(cardId) },
            removeAction = { savingRepository.removeCard(cardId) },
        ) {
            updateArticleState { details ->
                details.updateCard(cardId) { it.copy(isSaved = isSaved) }
            }
        }
    }

    private suspend fun handleSaving(
        isSaved: Boolean,
        saveAction: suspend () -> Result<Int>,
        removeAction: suspend () -> Result<Int>,
        onSuccess: (Int) -> Unit
    ) {
        val result = if (isSaved) saveAction() else removeAction()
        result.onSuccess { savedCount ->
            onSuccess(savedCount)
        }.onFailure { throwable ->
            SnackbarController.showErrorMessage(throwable)
        }
    }

    private fun updateArticleState(transform: (ArticleDetails) -> ArticleDetails) {
        _articleState.update { state ->
            state.copy(details = state.details?.let(transform))
        }
    }
}
