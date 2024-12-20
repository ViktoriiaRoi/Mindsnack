package com.comppot.mindsnack.core.common

sealed class CustomException : Exception() {
    object NoInternetConnection : CustomException()
    object ServerError : CustomException()
    object UnknownError : CustomException()
}
