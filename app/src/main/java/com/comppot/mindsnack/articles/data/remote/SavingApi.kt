package com.comppot.mindsnack.articles.data.remote

import com.comppot.mindsnack.articles.data.remote.dto.ArticleDTO
import com.comppot.mindsnack.articles.data.remote.dto.SavedResponseDTO
import com.comppot.mindsnack.core.data.Page
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SavingApi {
    @GET("/saved/articles")
    suspend fun getSavedArticles(@Query("page") page: Int): Response<Page<ArticleDTO>>

    @POST("/saved/articles/add")
    suspend fun saveArticle(@Query("articleId") articleId: Long): Response<SavedResponseDTO>

    @DELETE("/saved/articles/delete")
    suspend fun removeArticle(@Query("articleId") articleId: Long): Response<SavedResponseDTO>
}
