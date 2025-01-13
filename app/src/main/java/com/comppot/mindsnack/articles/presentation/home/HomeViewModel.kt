package com.comppot.mindsnack.articles.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.comppot.mindsnack.articles.domain.model.Article
import com.comppot.mindsnack.articles.domain.model.Category
import com.comppot.mindsnack.articles.domain.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    private val _articlesState = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val articlesState = _articlesState.asStateFlow()

    init {
        initState()
    }

    private fun initState() {
        fetchCategories()
        fetchRecommendations(Category.ALL)
    }

    fun selectCategory(category: Category) = viewModelScope.launch {
        _homeState.update { it.copy(selectedCategory = category) }
        _articlesState.value = PagingData.empty()
        delay(1)
        fetchRecommendations(category)
    }

    private fun fetchCategories() = viewModelScope.launch {
        articleRepository.getCategories().onSuccess { categories ->
            val displayedCategories = listOf(Category.ALL) + categories
            _homeState.update { it.copy(categories = displayedCategories) }
        }
    }

    private fun fetchRecommendations(category: Category) = viewModelScope.launch {
        articleRepository.getRecommendations(category)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect { paging ->
                _articlesState.value = paging
            }
    }
}
