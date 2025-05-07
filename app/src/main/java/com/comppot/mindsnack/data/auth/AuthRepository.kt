package com.comppot.mindsnack.data.auth

import android.content.Context
import com.comppot.mindsnack.model.User
import com.comppot.mindsnack.model.toUser
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

interface AuthRepository {
    fun isAuthorized(): Boolean
    fun getUser(): User
    fun logout(context: Context, onComplete: () -> Unit)
}

class AuthRepositoryImpl : AuthRepository {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val authUI: AuthUI = AuthUI.getInstance()

    override fun isAuthorized() = firebaseAuth.currentUser != null

    override fun getUser(): User {
        val firebaseUser = firebaseAuth.currentUser
        return firebaseUser?.toUser() ?: User()
    }

    override fun logout(context: Context, onComplete: () -> Unit) {
        authUI.signOut(context)
            .addOnCompleteListener {
                onComplete()
            }
    }
}
