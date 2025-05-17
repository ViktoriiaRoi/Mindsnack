package com.comppot.mindsnack.notifications.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.comppot.mindsnack.notifications.domain.model.Notification
import com.comppot.mindsnack.notifications.domain.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _notificationsState = MutableStateFlow<PagingData<Notification>>(PagingData.empty())
    val notificationsState = _notificationsState.asStateFlow()

    private val seenNotificationIds = mutableSetOf<Long>()

    init {
        fetchNotifications()
    }

    fun readNotification(notification: Notification) {
        if (seenNotificationIds.add(notification.id)) {
            viewModelScope.launch {
                notificationRepository.readNotification(notification.id)
            }
        }
    }

    private fun fetchNotifications() = viewModelScope.launch {
        notificationRepository.getNotifications()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect { paging ->
                _notificationsState.value = paging
            }
    }
}
