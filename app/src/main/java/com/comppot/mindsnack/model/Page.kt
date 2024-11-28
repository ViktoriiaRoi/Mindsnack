package com.comppot.mindsnack.model

data class Page<T>(
    val elements: List<T>,
    val hasNext: Boolean
)
