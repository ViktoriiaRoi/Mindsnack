@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package com.comppot.mindsnack.articles.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.comppot.mindsnack.R
import com.comppot.mindsnack.articles.domain.repository.ArticleRepository
import com.comppot.mindsnack.core.presentation.utils.SnackbarController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _queryState = MutableStateFlow("")

    val articlesState = _queryState.debounce(300.milliseconds).flatMapLatest { query ->
        articleRepository.searchArticles(query)
    }.cachedIn(viewModelScope)

    fun searchArticles(query: String) = viewModelScope.launch {
        _queryState.value = query
    }

    fun suggestBook(title: String, author: String) = viewModelScope.launch {
        articleRepository.suggestArticle(title, author)
            .onSuccess { SnackbarController.showMessage(R.string.search_screen_suggestion_succes) }
            .onFailure { SnackbarController.showErrorMessage(it) }
    }
}
