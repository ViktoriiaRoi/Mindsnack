package com.comppot.mindsnack.core.data

data class Page<T>(
    val articles: List<T>,
    val hasNext: Boolean
)
