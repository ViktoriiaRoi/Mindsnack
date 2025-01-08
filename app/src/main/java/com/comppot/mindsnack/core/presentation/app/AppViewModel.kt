package com.comppot.mindsnack.core.presentation.app

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.auth.domain.AuthRepository
import com.comppot.mindsnack.core.presentation.Screen
import com.comppot.mindsnack.notifications.domain.repository.NotificationRepository
import com.comppot.mindsnack.profile.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    val appState = combine(
        profileRepository.userPreferences,
        notificationRepository.unreadCount
    ) { preferences, unreadCount ->
        AppState(preferences.themeMode, unreadCount)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    init {
        initState()
    }

    private fun initState() = viewModelScope.launch {
        notificationRepository.fetchUnreadCount()
    }

    fun getStartDestination(): Screen =
        if (authRepository.isAuthorized()) Screen.Tab else Screen.Login

    fun logout(context: Context, onComplete: () -> Unit) {
        authRepository.logout(context) {
            viewModelScope.launch {
                profileRepository.clearPreferences()
                onComplete()
            }
        }
    }
}
