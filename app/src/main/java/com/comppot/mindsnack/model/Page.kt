package com.comppot.mindsnack.model

data class Page<T>(
    val articles: List<T>,
    val hasNext: Boolean
)
