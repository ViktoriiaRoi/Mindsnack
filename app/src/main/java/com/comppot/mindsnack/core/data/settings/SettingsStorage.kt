package com.comppot.mindsnack.core.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import com.comppot.mindsnack.profile.domain.model.ThemeMode
import com.comppot.mindsnack.profile.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface SettingsStorage {
    val userPreferences: Flow<UserPreferences>
    val loginTimestamp: Flow<Long>

    suspend fun updateThemeMode(themeMode: Int)
    suspend fun updateNotifications(notifications: Boolean)
    suspend fun updateLoginTimestamp(timestamp: Long)
    suspend fun clear()
}

class SettingsStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsStorage {

    override val userPreferences: Flow<UserPreferences> = dataStore.data.map { preferences ->
        preferences.getUserPreferences()
    }

    override val loginTimestamp: Flow<Long> = dataStore.data.map { preferences ->
        preferences[LOGIN_TIMESTAMP] ?: System.currentTimeMillis()
    }

    override suspend fun updateThemeMode(themeMode: Int) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_THEME_MODE] = themeMode
        }
    }

    override suspend fun updateNotifications(notifications: Boolean) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_NOTIFICATIONS] = notifications
        }
    }

    override suspend fun updateLoginTimestamp(timestamp: Long) {
        dataStore.edit { preferences ->
            preferences[LOGIN_TIMESTAMP] = timestamp
        }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    private fun Preferences.getUserPreferences(): UserPreferences {
        val themeMode = ThemeMode.fromId(this[PREFERENCE_THEME_MODE])
        val notifications = this[PREFERENCE_NOTIFICATIONS] ?: true
        return UserPreferences(themeMode, notifications)
    }

    companion object {
        private val PREFERENCE_THEME_MODE = intPreferencesKey("preference_theme_mode")
        private val PREFERENCE_NOTIFICATIONS = booleanPreferencesKey("preference_notifications")
        private val LOGIN_TIMESTAMP = longPreferencesKey("login_timestamp")
    }
}
