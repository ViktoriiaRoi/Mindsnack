package com.comppot.mindsnack.profile.data.remote.dto

import com.comppot.mindsnack.profile.domain.model.ThemeMode
import com.comppot.mindsnack.profile.domain.model.UserPreferences

data class PreferencesDTO(
    val theme: Int,
    val notificationsEnabled: Boolean
)

fun PreferencesDTO.toPreferences() = UserPreferences(
    themeMode = ThemeMode.fromId(theme),
    notifications = notificationsEnabled
)
