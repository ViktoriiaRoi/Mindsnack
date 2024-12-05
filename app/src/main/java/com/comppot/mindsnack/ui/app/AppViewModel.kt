package com.comppot.mindsnack.ui.app

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.data.auth.AuthRepository
import com.comppot.mindsnack.data.settings.SettingsRepository
import com.comppot.mindsnack.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val appState = settingsRepository.userPreferences.map { preferences ->
        AppState(preferences.themeMode)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun getStartDestination(): Screen =
        if (authRepository.isAuthorized()) Screen.Tab else Screen.Login

    fun logout(context: Context, onComplete: () -> Unit) {
        authRepository.logout(context) {
            viewModelScope.launch {
                settingsRepository.clear()
                onComplete()
            }
        }
    }
}
