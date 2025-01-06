package com.comppot.mindsnack.notifications.domain.repository

import com.comppot.mindsnack.notifications.domain.model.Notification

interface NotificationRepository {
    suspend fun getNotifications(page: Int = 1): Result<List<Notification>>
    suspend fun readNotification(id: Long): Result<Unit>
}
