package com.comppot.mindsnack.core.data.settings

import android.util.Log
import com.comppot.mindsnack.profile.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "SettingsRepository"

interface SettingsRepository {
    val userPreferences: Flow<UserPreferences>
    val loginTimestamp: Flow<Long>

    suspend fun updateThemeMode(themeMode: Int)
    suspend fun updateNotifications(notifications: Boolean)
    suspend fun setLoginTimeToNow()
    suspend fun clear()
}

class SettingsRepositoryImpl @Inject constructor(
    private val settingsStorage: SettingsStorage
) : SettingsRepository {

    override val userPreferences: Flow<UserPreferences> = settingsStorage.userPreferences
    override val loginTimestamp: Flow<Long> = settingsStorage.loginTimestamp

    override suspend fun updateThemeMode(themeMode: Int) {
        settingsStorage.updateThemeMode(themeMode)
    }

    override suspend fun updateNotifications(notifications: Boolean) {
        settingsStorage.updateNotifications(notifications)
    }

    override suspend fun setLoginTimeToNow() {
        val now = System.currentTimeMillis() / 1000
        settingsStorage.updateLoginTimestamp(now)
    }

    override suspend fun clear() {
        settingsStorage.clear()
        Log.d(TAG, "Settings are cleared")
    }
}
