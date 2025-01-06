package com.comppot.mindsnack.notifications.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor() : ViewModel() {

    private val _notificationState = MutableStateFlow(NotificationState())
    val notificationState = _notificationState.asStateFlow()

    init {
        initState()
    }

    private fun initState() = viewModelScope.launch {
        _notificationState.emit(
            _notificationState.value.copy(
                isLoading = false,
                notifications = emptyList()
            )
        )
    }
}
