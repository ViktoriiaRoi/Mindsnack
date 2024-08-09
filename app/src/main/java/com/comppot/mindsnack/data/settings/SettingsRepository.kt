package com.comppot.mindsnack.data.settings

import android.util.Log
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "SettingsRepository"

interface SettingsRepository {
    val savedArticleIds: Flow<List<Long>>
    suspend fun saveArticle(id: Long)
    suspend fun removeArticle(id: Long)
}

class SettingsRepositoryImpl @Inject constructor(
    private val settingsStorage: SettingsStorage
) : SettingsRepository {

    override val savedArticleIds: Flow<List<Long>> = settingsStorage.savedArticleIds

    override suspend fun saveArticle(id: Long) {
        settingsStorage.saveArticle(id)
        Log.d(TAG, "Article $id was saved")
    }

    override suspend fun removeArticle(id: Long) {
        settingsStorage.removeArticle(id)
        Log.d(TAG, "Article $id was removed")
    }
}
