package com.comppot.mindsnack.ui.screens.tab.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.data.article.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {
    private val _savedState = MutableStateFlow(SavedState())
    val savedState = _savedState.asStateFlow()

    init {
        initState()
    }

    private fun initState() = viewModelScope.launch {
        _savedState.emit(
            _savedState.value.copy(
                isLoading = false,
                articles = articleRepository.getAllArticles()
            )
        )
    }
}
