package com.comppot.mindsnack.articles.presentation.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.articles.domain.repository.SavingRepository
import com.comppot.mindsnack.core.common.CustomException
import com.comppot.mindsnack.core.presentation.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val savingRepository: SavingRepository
) : ViewModel() {

    private val _savedState = MutableStateFlow(SavedState())
    val savedState = _savedState.asStateFlow()

    init {
        initState()
    }

    private fun initState() = viewModelScope.launch {
        fetchSavedArticles()
        fetchSavedCards()
    }

    private suspend fun fetchSavedArticles() {
        val result = savingRepository.getSavedArticles()
        val status = result.fold(
            onSuccess = { articles ->
                if (articles.isEmpty()) Status.Empty else Status.Success(articles)
            }, onFailure = { error -> Status.Error(error as CustomException) }
        )
        _savedState.update { it.copy(articlesStatus = status) }
    }

    private suspend fun fetchSavedCards() {
        val result = savingRepository.getSavedCards()
        val status = result.fold(
            onSuccess = { cards ->
                if (cards.isEmpty()) Status.Empty else Status.Success(cards)
            }, onFailure = { error -> Status.Error(error as CustomException) }
        )
        _savedState.update { it.copy(cardsStatus = status) }
    }
}
