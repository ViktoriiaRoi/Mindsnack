package com.comppot.mindsnack.model

data class UserPreferences(
    val themeMode: ThemeMode = ThemeMode.AUTO,
    val notifications: Boolean = true
)
