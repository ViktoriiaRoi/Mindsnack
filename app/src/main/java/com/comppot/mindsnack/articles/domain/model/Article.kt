package com.comppot.mindsnack.articles.domain.model

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("id")
    val id: Long,
    @SerializedName("image")
    val image: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("postDate")
    val postDate: Long,
    @SerializedName("numberOfCards")
    val numberOfCards: Int
)