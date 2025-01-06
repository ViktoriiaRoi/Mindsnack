package com.comppot.mindsnack.profile.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.comppot.mindsnack.profile.data.local.SettingsStorage
import com.comppot.mindsnack.profile.data.local.SettingsStorageImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    private const val SETTINGS_DATA_STORE = "settings"

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS_DATA_STORE)

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideSettingsStorage(dataStore: DataStore<Preferences>): SettingsStorage {
        return SettingsStorageImpl(dataStore)
    }
}
