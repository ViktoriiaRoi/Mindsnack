package com.comppot.mindsnack.core.di

import com.comppot.mindsnack.core.data.api.MindSnackApi
import com.comppot.mindsnack.articles.data.repository.ArticleRepositoryImpl
import com.comppot.mindsnack.articles.domain.repository.ArticleRepository
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
    fun provideArticleRepository(api: MindSnackApi): ArticleRepository {
        return ArticleRepositoryImpl(api)
    }

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
