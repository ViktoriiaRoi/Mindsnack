package com.comppot.mindsnack.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface SettingsStorage {
    val savedArticleIds: Flow<List<Long>>
    suspend fun saveArticle(id: Long)
    suspend fun removeArticle(id: Long)
}

class SettingsStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson
) : SettingsStorage {

    override val savedArticleIds: Flow<List<Long>> = dataStore.data.map { preferences ->
        preferences.getSavedArticleIds()
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

    private fun Preferences.getSavedArticleIds(): MutableList<Long> {
        val json = this[SAVED_ARTICLE_IDS] ?: "[]"
        val type = object : TypeToken<List<Long>>() {}.type
        return gson.fromJson(json, type)
    }

    companion object {
        private val SAVED_ARTICLE_IDS = stringPreferencesKey("saved_article_ids")
    }
}
