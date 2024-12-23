package com.comppot.mindsnack.articles.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.R
import com.comppot.mindsnack.articles.domain.model.Article
import com.comppot.mindsnack.articles.domain.repository.ArticleRepository
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
class SearchViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _searchStatus = MutableStateFlow<Status<List<Article>>>(Status.Loading)
    val searchStatus = _searchStatus.asStateFlow()

    init {
        initState()
    }

    private fun initState() = viewModelScope.launch {
        searchArticles("")
    }

    fun searchArticles(text: String) = viewModelScope.launch {
        _searchStatus.update { Status.Loading }
        val result = articleRepository.searchArticles(text)
        val status = result.fold(
            onSuccess = { if (it.isEmpty()) Status.Empty else Status.Success(it) },
            onFailure = { error -> Status.Error(error as CustomException) }
        )
        _searchStatus.update { status }
    }

    fun suggestBook(title: String, author: String) = viewModelScope.launch {
        articleRepository.suggestArticle(title, author)
            .onSuccess { SnackbarController.showMessage(R.string.search_screen_suggestion_succes) }
            .onFailure { SnackbarController.showErrorMessage(it) }
    }
}
