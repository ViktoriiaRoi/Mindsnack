package com.comppot.mindsnack.profile.presentation.profile

import com.comppot.mindsnack.profile.domain.model.User
import com.comppot.mindsnack.profile.domain.model.UserPreferences

data class ProfileState(
    val user: User = User(),
    val preferences: UserPreferences = UserPreferences()
)
