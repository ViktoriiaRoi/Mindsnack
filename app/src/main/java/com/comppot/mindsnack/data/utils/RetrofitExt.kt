package com.comppot.mindsnack.data.utils

import retrofit2.Response

fun <T> Response<T>.toResult(): Result<T> {
    val body = this.body()
    return if (isSuccessful && body != null) {
        Result.success(body)
    } else {
        val message = errorBody()?.string() ?: message()
        Result.failure(Exception(message))
    }
}

suspend fun <T> runSafe(block: suspend () -> Response<T>): Result<T> {
    return try {
        block().toResult()
    } catch (e: Exception) {
        Result.failure(e)
    }
}
