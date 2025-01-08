package com.comppot.mindsnack.notifications.domain.repository

import com.comppot.mindsnack.notifications.domain.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    val unreadCount: Flow<Int>
    suspend fun getNotifications(page: Int = 1): Result<List<Notification>>
    suspend fun readNotification(id: Long)
    suspend fun fetchUnreadCount()
}
