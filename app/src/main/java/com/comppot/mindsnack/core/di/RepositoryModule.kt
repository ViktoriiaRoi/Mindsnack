package com.comppot.mindsnack.core.di

import com.comppot.mindsnack.auth.data.AuthRepositoryImpl
import com.comppot.mindsnack.auth.domain.AuthRepository
import com.comppot.mindsnack.core.data.settings.SettingsRepository
import com.comppot.mindsnack.core.data.settings.SettingsRepositoryImpl
import com.comppot.mindsnack.core.data.settings.SettingsStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(settingsStorage: SettingsStorage): SettingsRepository {
        return SettingsRepositoryImpl(settingsStorage)
    }
}
