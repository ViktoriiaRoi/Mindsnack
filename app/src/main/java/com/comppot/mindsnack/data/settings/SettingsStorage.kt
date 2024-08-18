package com.comppot.mindsnack.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.comppot.mindsnack.model.UserPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface SettingsStorage {
    val savedArticleIds: Flow<List<Long>>
    val userPreferences: Flow<UserPreferences>

    suspend fun saveArticle(id: Long)
    suspend fun removeArticle(id: Long)
    suspend fun updateDarkTheme(darkTheme: Boolean)
    suspend fun updateNotifications(notifications: Boolean)
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

    override suspend fun updateDarkTheme(darkTheme: Boolean) {
        dataStore.edit { preferences ->
            preferences[PREFERENCE_DARK_THEME] = darkTheme
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

    private fun Preferences.getSavedArticleIds(): MutableList<Long> {
        val json = this[SAVED_ARTICLE_IDS] ?: "[]"
        val type = object : TypeToken<List<Long>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun Preferences.getUserPreferences(): UserPreferences {
        val darkTheme = this[PREFERENCE_DARK_THEME] ?: false
        val notifications = this[PREFERENCE_NOTIFICATIONS] ?: true
        return UserPreferences(darkTheme, notifications)
    }

    companion object {
        private val SAVED_ARTICLE_IDS = stringPreferencesKey("saved_article_ids")
        private val PREFERENCE_DARK_THEME = booleanPreferencesKey("preference_dark_theme")
        private val PREFERENCE_NOTIFICATIONS = booleanPreferencesKey("preference_notifications")
    }
}
