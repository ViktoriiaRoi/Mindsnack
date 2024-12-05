package com.comppot.mindsnack.data.utils

import com.comppot.mindsnack.data.common.CustomException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> Response<T>.toResult(): Result<T> {
    val body = this.body()
    return if (isSuccessful && body != null) {
        Result.success(body)
    } else {
        val exception = when (code()) {
            404, 500 -> CustomException.ServerError
            else -> CustomException.UnknownError
        }
        Result.failure(exception)
    }
}

suspend fun <T> runSafe(block: suspend () -> Response<T>): Result<T> {
    return try {
        block().toResult()
    } catch (e: UnknownHostException) {
        Result.failure(CustomException.NoInternetConnection)
    } catch (e: SocketTimeoutException) {
        Result.failure(CustomException.ServerError)
    } catch (e: Exception) {
        Result.failure(CustomException.UnknownError)
    }
}
