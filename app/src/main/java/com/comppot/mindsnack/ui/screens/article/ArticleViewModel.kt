package com.comppot.mindsnack.ui.screens.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.data.article.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _articleState = MutableStateFlow(ArticleState())
    val articleState = _articleState.asStateFlow()

    fun fetchArticleDetails(id: Long) = viewModelScope.launch {
        val articleDetails = articleRepository.getArticleById(id)
        _articleState.emit(
            ArticleState(
                isLoading = false,
                articleDetails = articleDetails,
            )
        )
    }
}
