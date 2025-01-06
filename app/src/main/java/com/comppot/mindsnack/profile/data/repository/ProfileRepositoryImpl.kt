package com.comppot.mindsnack.profile.data.repository

import com.comppot.mindsnack.core.data.utils.runSafe
import com.comppot.mindsnack.profile.data.local.SettingsStorage
import com.comppot.mindsnack.profile.data.remote.ProfileApi
import com.comppot.mindsnack.profile.data.remote.dto.toPreferences
import com.comppot.mindsnack.profile.data.remote.dto.toUser
import com.comppot.mindsnack.profile.domain.model.UserPreferences
import com.comppot.mindsnack.profile.domain.model.ThemeMode
import com.comppot.mindsnack.profile.domain.model.User
import com.comppot.mindsnack.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApi,
    private val storage: SettingsStorage
) : ProfileRepository {
    override val userPreferences: Flow<UserPreferences> = storage.userPreferences

    override suspend fun createProfile() {
        runSafe { api.createProfile() }.onSuccess {
            savePreferences(it.preferences.toPreferences())
        }
    }

    override suspend fun getUser(): Result<User> {
        return runSafe { api.getUser() }.map { it.toUser() }
    }

    override suspend fun updatePreferences(themeMode: ThemeMode?) {
        themeMode?.let {
            storage.updateThemeMode(it)
        }
        runSafe { api.setPreferences(themeId = themeMode?.id) }
    }

    override suspend fun clearPreferences() {
        storage.clear()
    }

    private suspend fun savePreferences(preferences: UserPreferences) {
        storage.updateThemeMode(preferences.themeMode)
        storage.updateNotifications(preferences.notifications)
    }
}
