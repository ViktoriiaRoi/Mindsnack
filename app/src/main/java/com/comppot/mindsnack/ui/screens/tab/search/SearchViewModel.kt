package com.comppot.mindsnack.ui.screens.tab.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.data.article.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        _searchState.emit(
            _searchState.value.copy(
                isLoading = false,
                articles = articleRepository.getAllArticles()
            )
        )
    }

    fun searchArticles(text: String) = viewModelScope.launch {
        val articles = articleRepository.getAllArticles()
        val filteredArticles = articles.filter { it.title.contains(text.trim(), ignoreCase = true) }

        _searchState.emit(
            _searchState.value.copy(
                articles = filteredArticles
            )
        )
    }
}
