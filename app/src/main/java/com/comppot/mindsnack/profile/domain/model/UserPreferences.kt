package com.comppot.mindsnack.profile.domain.model

data class UserPreferences(
    val themeMode: ThemeMode = ThemeMode.AUTO,
    val notifications: Boolean = true
)
