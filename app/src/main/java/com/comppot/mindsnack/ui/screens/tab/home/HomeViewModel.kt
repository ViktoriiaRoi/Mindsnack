package com.comppot.mindsnack.ui.screens.tab.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.data.article.ArticleRepository
import com.comppot.mindsnack.model.Article
import com.comppot.mindsnack.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        _homeState.emit(
            _homeState.value.copy(
                isLoading = false,
                categories = getCategories(),
                articles = articleRepository.getAllArticles()
            )
        )
    }

    fun selectCategory(category: Category) = viewModelScope.launch {
        _homeState.emit(
            _homeState.value.copy(
                selectedCategory = category,
                articles = getArticlesByCategory(category)
            )
        )
    }

    private fun getCategories(): List<Category> {
        val categories = articleRepository.getCategories()
        return listOf(Category.ALL) + categories
    }

    private fun getArticlesByCategory(category: Category): List<Article> {
        val articles = articleRepository.getAllArticles()
        return when (category) {
            Category.ALL -> articles
            else -> articles.filter { it.categoryId == category.id }
        }
    }
}
