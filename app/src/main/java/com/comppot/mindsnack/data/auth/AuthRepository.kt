package com.comppot.mindsnack.data.auth

import android.content.Context
import com.comppot.mindsnack.model.User
import com.comppot.mindsnack.model.toUser
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.TimeUnit

interface AuthRepository {
    fun isAuthorized(): Boolean
    fun getUser(): User
    fun getToken(): String?
    fun logout(context: Context, onComplete: () -> Unit)
}

class AuthRepositoryImpl : AuthRepository {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val authUI: AuthUI = AuthUI.getInstance()

    private val currentUser get() = firebaseAuth.currentUser

    override fun isAuthorized() = currentUser != null

    override fun getUser(): User = currentUser?.toUser() ?: User()

    override fun getToken(): String? = try {
        currentUser?.let {
            val task = it.getIdToken(false)
            val result = Tasks.await(task, 20, TimeUnit.SECONDS)
            result.token
        }
    } catch (e: Exception) {
        null
    }

    override fun logout(context: Context, onComplete: () -> Unit) {
        authUI.signOut(context)
            .addOnCompleteListener {
                onComplete()
            }
    }
}
