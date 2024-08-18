package com.comppot.mindsnack.ui.screens.login

import android.content.Intent
import com.firebase.ui.auth.AuthUI

object LoginHelper {
    private val authUI: AuthUI = AuthUI.getInstance()

    fun getSignInIntent(): Intent {
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build(),
        )
        return authUI.createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
    }
}
