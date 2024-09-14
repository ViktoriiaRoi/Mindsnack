package com.comppot.mindsnack.ui.screens.tab.profile

import com.comppot.mindsnack.model.User
import com.comppot.mindsnack.model.UserPreferences

data class ProfileState(
    val user: User = User(),
    val preferences: UserPreferences = UserPreferences()
)
