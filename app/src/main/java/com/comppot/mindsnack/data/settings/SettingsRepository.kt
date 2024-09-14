package com.comppot.mindsnack.data.settings

import android.util.Log
import com.comppot.mindsnack.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "SettingsRepository"

interface SettingsRepository {
    val savedArticleIds: Flow<List<Long>>
    val userPreferences: Flow<UserPreferences>
    val loginTimestamp: Flow<Long>

    suspend fun saveArticle(id: Long)
    suspend fun removeArticle(id: Long)
    suspend fun updateDarkTheme(darkTheme: Boolean)
    suspend fun updateNotifications(notifications: Boolean)
    suspend fun setLoginTimeToNow()
    suspend fun clear()
}

class SettingsRepositoryImpl @Inject constructor(
    private val settingsStorage: SettingsStorage
) : SettingsRepository {

    override val savedArticleIds: Flow<List<Long>> = settingsStorage.savedArticleIds
    override val userPreferences: Flow<UserPreferences> = settingsStorage.userPreferences
    override val loginTimestamp: Flow<Long> = settingsStorage.loginTimestamp

    override suspend fun saveArticle(id: Long) {
        settingsStorage.saveArticle(id)
        Log.d(TAG, "Article $id was saved")
    }

    override suspend fun removeArticle(id: Long) {
        settingsStorage.removeArticle(id)
        Log.d(TAG, "Article $id was removed")
    }

    override suspend fun updateDarkTheme(darkTheme: Boolean) {
        settingsStorage.updateDarkTheme(darkTheme)
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
