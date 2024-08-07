package com.comppot.mindsnack.di

import com.comppot.mindsnack.data.article.ArticleRepository
import com.comppot.mindsnack.data.article.ArticleRepositoryImpl
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
}
