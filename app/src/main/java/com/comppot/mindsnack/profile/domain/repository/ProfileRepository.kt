package com.comppot.mindsnack.profile.domain.repository

import com.comppot.mindsnack.profile.domain.model.UserPreferences
import com.comppot.mindsnack.profile.domain.model.ThemeMode
import com.comppot.mindsnack.profile.domain.model.User
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    val userPreferences: Flow<UserPreferences>
    suspend fun createProfile()
    suspend fun getUser(): Result<User>
    suspend fun updatePreferences(themeMode: ThemeMode?)
    suspend fun clearPreferences()
}
