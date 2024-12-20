package com.comppot.mindsnack.notifications.presentation.notification

import com.comppot.mindsnack.notifications.domain.model.Notification

data class NotificationState (
    val isLoading: Boolean = true,
    val notifications: List<Notification> = emptyList(),
)