package com.comppot.mindsnack.notifications.data.remote.dto

import com.comppot.mindsnack.notifications.domain.model.Notification

data class NotificationDTO(
    val id: Long,
    val title: String,
    val text: String,
    val sentDate: Long,
    val wasRead: Boolean
)

fun NotificationDTO.toNotification() = Notification(
    id = id,
    title = title,
    text = text,
    sentDate = sentDate,
    wasRead = wasRead
)
