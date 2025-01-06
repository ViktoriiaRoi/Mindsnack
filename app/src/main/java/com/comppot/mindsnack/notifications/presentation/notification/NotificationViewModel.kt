package com.comppot.mindsnack.notifications.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.core.common.CustomException
import com.comppot.mindsnack.core.presentation.Status
import com.comppot.mindsnack.notifications.domain.model.Notification
import com.comppot.mindsnack.notifications.domain.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _notificationState = MutableStateFlow(NotificationState())
    val notificationState = _notificationState.asStateFlow()

    init {
        initState()
    }

    private fun initState() = viewModelScope.launch {
        fetchNotifications()
    }

    fun readNotification(notification: Notification) = viewModelScope.launch {
        notificationRepository.readNotification(notification.id)
    }

    private suspend fun fetchNotifications() {
        val result = notificationRepository.getNotifications()
        val status = result.fold(
            onSuccess = { if (it.isEmpty()) Status.Empty else Status.Success(it) },
            onFailure = { error -> Status.Error(error as CustomException) }
        )
        _notificationState.emit(NotificationState(status))
    }
}
