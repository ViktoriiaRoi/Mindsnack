package com.comppot.mindsnack.core.data.utils

import com.comppot.mindsnack.core.common.CustomException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> Response<T>.toResult(): Result<T> {
    val body = this.body()
    return if (isSuccessful && body != null) {
        Result.success(body)
    } else {
        Result.failure(CustomException.ServerError)
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
