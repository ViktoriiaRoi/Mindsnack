package com.comppot.mindsnack.notifications.data.remote

import com.comppot.mindsnack.core.data.Page
import com.comppot.mindsnack.notifications.data.remote.dto.NotificationDTO
import com.comppot.mindsnack.notifications.data.remote.dto.UnreadResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface NotificationApi {
    @GET("/notifications")
    suspend fun getNotifications(@Query("page") page: Int): Response<Page<NotificationDTO>>

    @PUT("/notifications/read")
    suspend fun readNotification(@Query("notificationId") id: Long): Response<Unit>

    @GET("/notifications/unread")
    suspend fun getUnreadCount(): Response<UnreadResponseDTO>
}
