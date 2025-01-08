package com.comppot.mindsnack.notifications.data.repository

import com.comppot.mindsnack.core.data.utils.runSafe
import com.comppot.mindsnack.notifications.data.local.NotificationStorage
import com.comppot.mindsnack.notifications.data.remote.NotificationApi
import com.comppot.mindsnack.notifications.data.remote.dto.toNotification
import com.comppot.mindsnack.notifications.domain.model.Notification
import com.comppot.mindsnack.notifications.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val api: NotificationApi,
    private val storage: NotificationStorage
) : NotificationRepository {
    override val unreadCount: Flow<Int> = storage.unreadCount

    override suspend fun getNotifications(page: Int): Result<List<Notification>> {
        return runSafe { api.getNotifications(page) }
            .map { it.objects.map { notificationDTO -> notificationDTO.toNotification() } }
    }

    override suspend fun readNotification(id: Long) {
        runSafe { api.readNotification(id) }.onSuccess {
            storage.decrementUnreadCount()
        }
    }

    override suspend fun fetchUnreadCount() {
        runSafe { api.getUnreadCount() }.onSuccess {
            storage.setUnreadCount(it.unreadCount)
        }
    }
}
