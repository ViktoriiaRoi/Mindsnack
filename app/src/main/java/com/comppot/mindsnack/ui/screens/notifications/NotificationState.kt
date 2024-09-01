package com.comppot.mindsnack.ui.screens.notifications

import com.comppot.mindsnack.model.Notification

data class NotificationState (
    val isLoading: Boolean = true,
    val notifications: List<Notification> = emptyList(),
)