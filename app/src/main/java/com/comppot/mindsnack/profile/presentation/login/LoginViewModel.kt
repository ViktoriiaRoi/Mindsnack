package com.comppot.mindsnack.profile.presentation.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.core.data.settings.SettingsRepository
import com.firebase.ui.auth.AuthUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

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

    fun onLoginSuccess() = viewModelScope.launch {
        settingsRepository.setLoginTimeToNow()
    }
}
