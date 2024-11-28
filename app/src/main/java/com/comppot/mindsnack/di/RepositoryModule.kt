package com.comppot.mindsnack.di

import android.content.Context
import com.comppot.mindsnack.data.api.MindSnackApi
import com.comppot.mindsnack.data.article.ArticleRepository
import com.comppot.mindsnack.data.article.ArticleRepositoryImpl
import com.comppot.mindsnack.data.article.LocalArticleRepository
import com.comppot.mindsnack.data.auth.AuthRepository
import com.comppot.mindsnack.data.auth.AuthRepositoryImpl
import com.comppot.mindsnack.data.settings.SettingsRepository
import com.comppot.mindsnack.data.settings.SettingsRepositoryImpl
import com.comppot.mindsnack.data.settings.SettingsStorage
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
