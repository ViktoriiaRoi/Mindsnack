package com.comppot.mindsnack.ui.screens.tab.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.data.auth.AuthRepository
import com.comppot.mindsnack.data.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val profileState = settingsRepository.userPreferences.map { preferences ->
        ProfileState(
            user = authRepository.getUser(),
            preferences = preferences
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileState()
    )


    fun updateDarkTheme(value: Boolean) = viewModelScope.launch {
        settingsRepository.updateDarkTheme(value)
    }

    fun updateNotifications(value: Boolean) = viewModelScope.launch {
        settingsRepository.updateNotifications(value)
    }
}
