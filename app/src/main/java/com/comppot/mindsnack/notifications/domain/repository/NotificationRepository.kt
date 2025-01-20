package com.comppot.mindsnack.notifications.domain.repository

import androidx.paging.PagingData
import com.comppot.mindsnack.notifications.domain.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    val unreadCount: Flow<Int>
    suspend fun getNotifications(): Flow<PagingData<Notification>>
    suspend fun readNotification(id: Long)
    suspend fun fetchUnreadCount()
}
