package com.comppot.mindsnack.notifications.data.repository

import com.comppot.mindsnack.core.data.utils.runSafe
import com.comppot.mindsnack.notifications.data.remote.NotificationApi
import com.comppot.mindsnack.notifications.data.remote.dto.toNotification
import com.comppot.mindsnack.notifications.domain.model.Notification
import com.comppot.mindsnack.notifications.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val api: NotificationApi
) : NotificationRepository {

    override suspend fun getNotifications(page: Int): Result<List<Notification>> {
        return runSafe { api.getNotifications(page) }
            .map { it.objects.map { notificationDTO -> notificationDTO.toNotification() } }
    }

    override suspend fun readNotification(id: Long): Result<Unit> {
        return runSafe { api.readNotification(id) }
    }
}
