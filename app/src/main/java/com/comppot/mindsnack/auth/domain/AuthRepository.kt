package com.comppot.mindsnack.auth.domain

import android.content.Context
import com.comppot.mindsnack.profile.domain.model.User

interface AuthRepository {
    fun isAuthorized(): Boolean
    fun getUser(): User
    fun getToken(): String?
    fun logout(context: Context, onComplete: () -> Unit)
}
