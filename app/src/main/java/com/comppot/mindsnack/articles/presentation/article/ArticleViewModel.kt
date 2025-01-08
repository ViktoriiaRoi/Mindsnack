package com.comppot.mindsnack.articles.presentation.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.R
import com.comppot.mindsnack.articles.domain.model.Rating
import com.comppot.mindsnack.articles.domain.repository.ArticleRepository
import com.comppot.mindsnack.articles.domain.repository.SavingRepository
import com.comppot.mindsnack.core.common.CustomException
import com.comppot.mindsnack.core.presentation.Status
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
            .onSuccess {
                articleId = id
                _articleState.emit(
                    ArticleState(
                        detailsStatus = Status.Success(it),
                        isSaved = it.isSaved,
                        savedCount = it.savedCount
                    )
                )
            }.onFailure { error ->
                _articleState.emit(
                    ArticleState(Status.Error(error as CustomException))
                )
            }
    }

    fun updateArticleSaved(isSaved: Boolean) = articleId?.let { id ->
        viewModelScope.launch {
            val result = if (isSaved) {
                savingRepository.saveArticle(id)
            } else {
                savingRepository.removeArticle(id)
            }

            result.onSuccess { savedCount ->
                _articleState.update { it.copy(isSaved = isSaved, savedCount = savedCount) }
            }.onFailure {
                SnackbarController.showErrorMessage(it)
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
}
