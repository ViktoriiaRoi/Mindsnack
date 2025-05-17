package com.comppot.mindsnack.auth.data

import com.comppot.mindsnack.auth.domain.AuthRepository
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val authRepository: AuthRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val idToken = authRepository.getToken()
        if (!idToken.isNullOrEmpty()) {
            requestBuilder.addHeader(AUTH_HEADER, "Bearer $idToken")
        }
        return chain.proceed(requestBuilder.build())
    }

    companion object {
        const val AUTH_HEADER = "Authorization"
    }
}
