package com.comppot.mindsnack.profile.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.profile.domain.model.ThemeMode
import com.comppot.mindsnack.profile.domain.model.User
import com.comppot.mindsnack.auth.domain.AuthRepository
import com.comppot.mindsnack.profile.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    // TODO: get user from profile repository
    private val user = authRepository.getUser() ?: User()

    val profileState = profileRepository.userPreferences.map { preferences ->
        ProfileState(
            user = user,
            preferences = preferences
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileState()
    )

    fun updateThemeMode(value: ThemeMode) = viewModelScope.launch {
        profileRepository.updatePreferences(themeMode = value)
    }
}
