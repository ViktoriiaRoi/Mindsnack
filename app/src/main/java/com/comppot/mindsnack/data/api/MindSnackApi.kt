package com.comppot.mindsnack.data.api

import com.comppot.mindsnack.model.Article
import com.comppot.mindsnack.model.ArticleDetails
import com.comppot.mindsnack.model.Category
import com.comppot.mindsnack.model.Page
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MindSnackApi {
    @GET("/home/topics")
    suspend fun getCategories(): Response<List<Category>>

    @GET("/home/recommendations")
    suspend fun getRecommendations(@Query("page") page: Int): Response<Page<Article>>

    @GET("/article/article")
    suspend fun getArticleDetails(@Query("articleID") id: Long): Response<ArticleDetails>

    @GET("/search/byname")
    suspend fun searchArticles(@Query("search_string") search: String): Response<List<Article>>
}
