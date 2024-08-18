package com.comppot.mindsnack.di

import com.comppot.mindsnack.data.article.ArticleRepository
import com.comppot.mindsnack.data.article.ArticleRepositoryImpl
import com.comppot.mindsnack.data.auth.AuthRepository
import com.comppot.mindsnack.data.auth.AuthRepositoryImpl
import com.comppot.mindsnack.data.settings.SettingsRepository
import com.comppot.mindsnack.data.settings.SettingsRepositoryImpl
import com.comppot.mindsnack.data.settings.SettingsStorage
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
    fun provideArticleRepository(): ArticleRepository {
        return ArticleRepositoryImpl()
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
