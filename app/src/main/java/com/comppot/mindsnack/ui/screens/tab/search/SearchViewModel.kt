package com.comppot.mindsnack.ui.screens.tab.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.R
import com.comppot.mindsnack.data.article.ArticleRepository
import com.comppot.mindsnack.ui.utils.SnackbarController
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

    private val _searchState = MutableStateFlow(SearchState())
    val searchState = _searchState.asStateFlow()

    init {
        initState()
    }

    private fun initState() = viewModelScope.launch {
        searchArticles("")
    }

    fun searchArticles(text: String) = viewModelScope.launch {
        _searchState.update { it.copy(isLoading = true) }
        articleRepository.searchArticles(text).onSuccess { articles ->
            _searchState.update { it.copy(isLoading = false, articles = articles) }
        }
    }

    fun suggestBook(title: String, author: String) = viewModelScope.launch {
        SnackbarController.showMessage(R.string.search_screen_suggestion_succes)
    }
}
