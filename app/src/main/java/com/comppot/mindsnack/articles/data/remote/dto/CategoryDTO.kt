package com.comppot.mindsnack.articles.data.remote.dto

import com.comppot.mindsnack.articles.domain.model.Category

data class CategoryDTO(
    val id: Int,
    val name: String
)

fun CategoryDTO.toCategory() = Category(
    id = id,
    name = name
)
