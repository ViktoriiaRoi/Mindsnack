package com.comppot.mindsnack.articles.data.remote

import com.comppot.mindsnack.articles.data.remote.dto.ArticleDTO
import com.comppot.mindsnack.articles.data.remote.dto.ArticleDetailsDTO
import com.comppot.mindsnack.articles.data.remote.dto.CategoryDTO
import com.comppot.mindsnack.core.data.Page
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleApi {
    @GET("/category")
    suspend fun getCategories(): Response<List<CategoryDTO>>

    @GET("/article/recommendations")
    suspend fun getRecommendations(
        @Query("category_id") categoryId: Int?,
        @Query("page") page: Int
    ): Response<Page<ArticleDTO>>

    @GET("/article/search")
    suspend fun searchArticles(
        @Query("search_string") searchString: String,
        @Query("page") page: Int
    ): Response<Page<ArticleDTO>>

    @GET("/article/details/{id}")
    suspend fun getArticleDetails(@Path("id") id: Long): Response<ArticleDetailsDTO>

    @POST("/article/read")
    suspend fun readArticle(@Query("articleId") id: Long): Response<Unit>

    @POST("/article/rating")
    suspend fun postRating(
        @Query("articleId") articleId: Long,
        @Query("score") score: Int
    ): Response<Unit>

    @POST("/article/suggest")
    suspend fun suggestArticle(
        @Query("title") title: String,
        @Query("author") author: String
    ): Response<Unit>
}
