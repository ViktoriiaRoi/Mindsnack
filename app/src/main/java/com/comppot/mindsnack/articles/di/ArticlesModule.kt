package com.comppot.mindsnack.articles.di

import androidx.paging.PagingConfig
import com.comppot.mindsnack.articles.data.remote.ArticleApi
import com.comppot.mindsnack.articles.data.remote.SavingApi
import com.comppot.mindsnack.articles.data.repository.ArticleRepositoryImpl
import com.comppot.mindsnack.articles.data.repository.SavingRepositoryImpl
import com.comppot.mindsnack.articles.domain.repository.ArticleRepository
import com.comppot.mindsnack.articles.domain.repository.SavingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ArticlesModule {
    @Provides
    @Singleton
    fun provideArticleApi(retrofit: Retrofit): ArticleApi =
        retrofit.create(ArticleApi::class.java)

    @Provides
    @Singleton
    fun provideSavingApi(retrofit: Retrofit): SavingApi =
        retrofit.create(SavingApi::class.java)

    @Provides
    @Singleton
    fun provideArticleRepository(api: ArticleApi): ArticleRepository {
        val pagingConfig = PagingConfig(pageSize = 10)
        return ArticleRepositoryImpl(api, pagingConfig)
    }

    @Provides
    @Singleton
    fun provideSavingRepository(api: SavingApi): SavingRepository {
        return SavingRepositoryImpl(api)
    }
}
