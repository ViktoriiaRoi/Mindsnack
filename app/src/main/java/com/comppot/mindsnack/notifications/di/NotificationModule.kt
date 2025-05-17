package com.comppot.mindsnack.notifications.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.paging.PagingConfig
import com.comppot.mindsnack.notifications.data.local.NotificationStorage
import com.comppot.mindsnack.notifications.data.local.NotificationStorageImpl
import com.comppot.mindsnack.notifications.data.remote.NotificationApi
import com.comppot.mindsnack.notifications.data.repository.NotificationRepositoryImpl
import com.comppot.mindsnack.notifications.domain.repository.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    private const val NOTIFICATION_DATA_STORE = "notification"

    private val Context.notificationDataStore: DataStore<Preferences> by preferencesDataStore(name = NOTIFICATION_DATA_STORE)

    @Provides
    @Singleton
    fun provideNotificationStorage(@ApplicationContext context: Context): NotificationStorage {
        return NotificationStorageImpl(context.notificationDataStore)
    }

    @Provides
    @Singleton
    fun provideNotificationApi(retrofit: Retrofit): NotificationApi =
        retrofit.create(NotificationApi::class.java)

    @Provides
    @Singleton
    fun provideNotificationRepository(
        api: NotificationApi,
        storage: NotificationStorage
    ): NotificationRepository {
        val pagingConfig = PagingConfig(pageSize = 10)
        return NotificationRepositoryImpl(api, storage, pagingConfig)
    }
}
