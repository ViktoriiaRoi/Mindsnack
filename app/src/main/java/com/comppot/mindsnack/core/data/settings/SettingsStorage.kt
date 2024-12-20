package com.comppot.mindsnack.core.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.comppot.mindsnack.profile.domain.model.ThemeMode
import com.comppot.mindsnack.profile.domain.model.UserPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface SettingsStorage {
    val savedArticleIds: Flow<List<Long>>
    val userPreferences: Flow<UserPreferences>
    val loginTimestamp: Flow<Long>

    suspend fun saveArticle(id: Long)
    suspend fun removeArticle(id: Long)
    suspend fun updateThemeMode(themeMode: Int)
    suspend fun updateNotifications(notifications: Boolean)
    suspend fun updateLoginTimestamp(timestamp: Long)
    suspend fun clear()
}

class SettingsStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson
) : SettingsStorage {

    override val savedArticleIds: Flow<List<Long>> = dataStore.data.map { preferences ->
        preferences.getSavedArticleIds()
    }

    override val userPreferences: Flow<UserPreferences> = dataStore.data.map { preferences ->
        preferences.getUserPreferences()
    }

    override val loginTimestamp: Flow<Long> = dataStore.data.map { preferences ->
        preferences[LOGIN_TIMESTAMP] ?: System.currentTimeMillis()
    }

    override suspend fun saveArticle(id: Long) {
        dataStore.edit { preferences ->
            val newList = preferences.getSavedArticleIds().apply {
                if (!contains(id)) add(0, id)
            }
            preferences[SAVED_ARTICLE_IDS] = gson.toJson(newList)
        }
    }

    override suspend fun removeArticle(id: Long) {
        dataStore.edit { preferences ->
            val newList = preferences.getSavedArticleIds().apply {
                if (contains(id)) remove(id)
            }
            preferences[SAVED_ARTICLE_IDS] = gson.toJson(newList)
        }
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

    private fun Preferences.getSavedArticleIds(): MutableList<Long> {
        val json = this[SAVED_ARTICLE_IDS] ?: "[]"
        val type = object : TypeToken<List<Long>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun Preferences.getUserPreferences(): UserPreferences {
        val themeMode = ThemeMode.fromId(this[PREFERENCE_THEME_MODE])
        val notifications = this[PREFERENCE_NOTIFICATIONS] ?: true
        return UserPreferences(themeMode, notifications)
    }

    companion object {
        private val SAVED_ARTICLE_IDS = stringPreferencesKey("saved_article_ids")
        private val PREFERENCE_THEME_MODE = intPreferencesKey("preference_theme_mode")
        private val PREFERENCE_NOTIFICATIONS = booleanPreferencesKey("preference_notifications")
        private val LOGIN_TIMESTAMP = longPreferencesKey("login_timestamp")
    }
}
