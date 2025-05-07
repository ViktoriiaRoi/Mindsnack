package com.comppot.mindsnack.core.presentation

import com.comppot.mindsnack.core.common.CustomException

sealed class Status<out T> {
    data object Loading : Status<Nothing>()
    data object Empty : Status<Nothing>()
    data class Error(val error: CustomException) : Status<Nothing>()
    data class Success<out T>(val data: T) : Status<T>()

    fun getDataOrNull(): T? = (this as? Success)?.data
}