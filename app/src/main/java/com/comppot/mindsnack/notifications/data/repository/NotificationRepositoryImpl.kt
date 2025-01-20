package com.comppot.mindsnack.notifications.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.comppot.mindsnack.core.data.utils.runSafe
import com.comppot.mindsnack.notifications.data.local.NotificationStorage
import com.comppot.mindsnack.notifications.data.paging.NotificationPagingSource
import com.comppot.mindsnack.notifications.data.remote.NotificationApi
import com.comppot.mindsnack.notifications.domain.model.Notification
import com.comppot.mindsnack.notifications.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val api: NotificationApi,
    private val storage: NotificationStorage,
    private val pagingConfig: PagingConfig
) : NotificationRepository {

    override val unreadCount: Flow<Int> = storage.unreadCount

    override suspend fun getNotifications(): Flow<PagingData<Notification>> {
        return Pager(pagingConfig) {
            NotificationPagingSource(api)
        }.flow
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
