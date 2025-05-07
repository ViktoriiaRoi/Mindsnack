package com.comppot.mindsnack.notifications.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface NotificationStorage {
    val unreadCount: Flow<Int>
    suspend fun setUnreadCount(unreadCount: Int)
    suspend fun decrementUnreadCount()
}

class NotificationStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : NotificationStorage {

    override val unreadCount: Flow<Int> = dataStore.data.map { preferences ->
        preferences[UNREAD_COUNT] ?: 0
    }

    override suspend fun setUnreadCount(unreadCount: Int) {
        dataStore.edit { preferences ->
            preferences[UNREAD_COUNT] = unreadCount
        }
    }

    override suspend fun decrementUnreadCount() {
        dataStore.edit { preferences ->
            preferences[UNREAD_COUNT]?.let { current ->
                preferences[UNREAD_COUNT] = current - 1
            }
        }
    }

    companion object {
        private val UNREAD_COUNT = intPreferencesKey("unread_count")
    }
}
