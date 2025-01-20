package com.comppot.mindsnack.articles.presentation.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.comppot.mindsnack.articles.domain.model.Article
import com.comppot.mindsnack.articles.domain.model.SavedCard
import com.comppot.mindsnack.articles.domain.repository.SavingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val savingRepository: SavingRepository
) : ViewModel() {

    private val _cardsState = MutableStateFlow<PagingData<SavedCard>>(PagingData.empty())
    val cardsState = _cardsState.asStateFlow()

    private val _articlesState = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val articlesState = _articlesState.asStateFlow()

    init {
        initState()
    }

    private fun initState() {
        fetchSavedArticles()
        fetchSavedCards()
    }

    private fun fetchSavedArticles() = viewModelScope.launch {
        savingRepository.getSavedArticles()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect { paging ->
                _articlesState.value = paging
            }
    }

    private fun fetchSavedCards() = viewModelScope.launch {
        savingRepository.getSavedCards()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect { paging ->
                _cardsState.value = paging
            }
    }
}
