package com.comppot.mindsnack.articles.domain.model

import com.google.gson.annotations.SerializedName

data class CardData(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String?,
    @SerializedName("text")
    val text: String,
    @SerializedName("isSaved")
    val isSaved: Boolean = false
) {
    val sharingText: String
        get() = listOfNotNull(title, text).joinToString("\n")
}
