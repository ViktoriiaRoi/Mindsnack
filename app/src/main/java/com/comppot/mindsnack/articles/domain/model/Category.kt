package com.comppot.mindsnack.articles.domain.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
) {
    companion object {
        val ALL = Category(0, "Усі")
    }
}