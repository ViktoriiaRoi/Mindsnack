package com.comppot.mindsnack.notifications.domain.model

import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("sentDate")
    val sentDate: Long,
    @SerializedName("wasRead")
    val wasRead: Boolean
)