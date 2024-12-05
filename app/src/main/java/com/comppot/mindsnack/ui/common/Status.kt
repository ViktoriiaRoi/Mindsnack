package com.comppot.mindsnack.ui.common

import com.comppot.mindsnack.data.common.CustomException

sealed class Status<out T> {
    data object Loading : Status<Nothing>()
    data object Empty : Status<Nothing>()
    data class Error(val error: CustomException) : Status<Nothing>()
    data class Success<out T>(val data: T) : Status<T>()

    fun getDataOrNull(): T? = (this as? Success)?.data
}