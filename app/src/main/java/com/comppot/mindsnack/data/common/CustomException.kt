package com.comppot.mindsnack.data.common

sealed class CustomException : Exception() {
    object NoInternetConnection : CustomException()
    object ServerError : CustomException()
    object UnknownError : CustomException()
}