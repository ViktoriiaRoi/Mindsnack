package com.comppot.mindsnack.profile.di

import com.comppot.mindsnack.profile.data.remote.ProfileApi
import com.comppot.mindsnack.profile.data.repository.ProfileRepositoryImpl
import com.comppot.mindsnack.profile.data.local.SettingsStorage
import com.comppot.mindsnack.profile.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    @Provides
    @Singleton
    fun provideProfileApi(retrofit: Retrofit): ProfileApi =
        retrofit.create(ProfileApi::class.java)

    @Provides
    @Singleton
    fun provideProfileRepository(api: ProfileApi, storage: SettingsStorage): ProfileRepository {
        return ProfileRepositoryImpl(api, storage)
    }
}
