package com.comppot.mindsnack.notifications.presentation.notification

import com.comppot.mindsnack.core.presentation.Status
import com.comppot.mindsnack.notifications.domain.model.Notification

data class NotificationState(
    val notificationStatus: Status<List<Notification>> = Status.Empty,
)
