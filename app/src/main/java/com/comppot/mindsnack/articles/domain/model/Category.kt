package com.comppot.mindsnack.articles.domain.model

data class Category(
    val id: Int,
    val name: String
) {
    companion object {
        val ALL = Category(0, "Усі")
    }
}