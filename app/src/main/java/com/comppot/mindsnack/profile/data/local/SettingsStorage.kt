package com.comppot.mindsnack.profile.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.comppot.mindsnack.profile.domain.model.ThemeMode
import com.comppot.mindsnack.profile.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface SettingsStorage {
    val userPreferences: Flow<UserPreferences>
    suspend fun updateThemeMode(themeMode: ThemeMode)
    suspend fun updateNotifications(notifications: Boolean)
    suspend fun clear()
}

class SettingsStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsStorage {

    override val userPreferences: Flow<UserPreferences> = dataStore.data.map { preferences ->
        preferences.getUserPreferences()
    }

    override suspend fun updateThemeMode(themeMode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_THEME_ID] = themeMode.id
        }
    }

    override suspend fun updateNotifications(notifications: Boolean) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_NOTIFICATIONS] = notifications
        }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    private fun Preferences.getUserPreferences(): UserPreferences {
        val themeMode = ThemeMode.fromId(this[PREFERENCE_THEME_ID])
        val notifications = this[PREFERENCE_NOTIFICATIONS] ?: true
        return UserPreferences(themeMode, notifications)
    }

    companion object {
        private val PREFERENCE_THEME_ID = intPreferencesKey("preference_theme_mode")
        private val PREFERENCE_NOTIFICATIONS = booleanPreferencesKey("preference_notifications")
    }
}
