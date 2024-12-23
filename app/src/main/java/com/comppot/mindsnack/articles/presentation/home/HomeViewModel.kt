package com.comppot.mindsnack.articles.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.core.common.CustomException
import com.comppot.mindsnack.articles.domain.model.Category
import com.comppot.mindsnack.articles.domain.repository.ArticleRepository
import com.comppot.mindsnack.core.presentation.Status
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
        fetchCategories()
        fetchRecommendations(Category.ALL)
    }

    fun selectCategory(category: Category) = viewModelScope.launch {
        _homeState.update { it.copy(selectedCategory = category) }
        fetchRecommendations(category)
    }

    private suspend fun fetchCategories() {
        articleRepository.getCategories().onSuccess { categories ->
            val displayedCategories = listOf(Category.ALL) + categories
            _homeState.update { it.copy(categories = displayedCategories) }
        }
    }

    private suspend fun fetchRecommendations(category: Category) {
        _homeState.update { it.copy(articlesStatus = Status.Loading) }
        val result = articleRepository.getRecommendations(category)
        val status = result.fold(
            onSuccess = { if (it.isEmpty()) Status.Empty else Status.Success(it) },
            onFailure = { error -> Status.Error(error as CustomException) }
        )
        _homeState.update { it.copy(articlesStatus = status) }
    }
}
