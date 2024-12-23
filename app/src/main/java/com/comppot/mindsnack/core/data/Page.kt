package com.comppot.mindsnack.core.data

import com.google.gson.annotations.SerializedName

data class Page<T>(
    val objects: List<T>,
    @SerializedName("has_next")
    val hasNext: Boolean
)
