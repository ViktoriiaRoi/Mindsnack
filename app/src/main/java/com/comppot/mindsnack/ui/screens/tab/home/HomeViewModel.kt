package com.comppot.mindsnack.ui.screens.tab.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.data.article.ArticleRepository
import com.comppot.mindsnack.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    init {
        initState()
    }

    private fun initState() = viewModelScope.launch {
        getCategories()
        getRecommendations(Category.ALL)
    }

    fun selectCategory(category: Category) = viewModelScope.launch {
        _homeState.update { it.copy(isLoading = true, selectedCategory = category) }
        getRecommendations(category)
    }

    private suspend fun getCategories() {
        articleRepository.getCategories().onSuccess { categories ->
            val displayedCategories = listOf(Category.ALL) + categories
            _homeState.update { it.copy(categories = displayedCategories) }
        }
    }

    private suspend fun getRecommendations(category: Category) {
        articleRepository.getRecommendations(category).onSuccess { articles ->
            _homeState.update { it.copy(isLoading = false, articles = articles) }
        }
    }
}
