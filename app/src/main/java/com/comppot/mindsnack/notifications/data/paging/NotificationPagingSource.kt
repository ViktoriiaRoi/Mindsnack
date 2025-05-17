package com.comppot.mindsnack.notifications.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.comppot.mindsnack.core.data.utils.runSafe
import com.comppot.mindsnack.notifications.data.remote.NotificationApi
import com.comppot.mindsnack.notifications.data.remote.dto.toNotification
import com.comppot.mindsnack.notifications.domain.model.Notification

class NotificationPagingSource(val api: NotificationApi) : PagingSource<Int, Notification>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notification> {
        val currentPage = params.key ?: 1
        val result = runSafe { api.getNotifications(currentPage) }
        return result.fold({ page ->
            LoadResult.Page(
                data = page.objects.map { it.toNotification() },
                prevKey = null,
                nextKey = if (page.hasNext) currentPage + 1 else null
            )
        }, { error -> LoadResult.Error(error) })
    }

    override fun getRefreshKey(state: PagingState<Int, Notification>): Int = 1
}
