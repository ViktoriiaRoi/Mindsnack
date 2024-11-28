package com.comppot.mindsnack.data.article

import android.content.Context
import com.comppot.mindsnack.data.utils.AssetUtils
import com.comppot.mindsnack.model.Article
import com.comppot.mindsnack.model.ArticleDetails
import com.comppot.mindsnack.model.Category
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class LocalArticleRepository @Inject constructor(
    private val context: Context,
    private val gson: Gson
) : ArticleRepository {

    private val articlesDetails: List<ArticleDetails> by lazy {
        loadArticles()
    }

    private val articlesList: List<Article>
        get() = articlesDetails.map {
            Article(
                id = it.id,
                image = it.image,
                title = it.title,
                postDate = it.postDate,
                numberOfCards = it.numberOfCards,
                categoryId = it.categoryId
            )
        }

    override suspend fun getRecommendations(category: Category): Result<List<Article>> {
        val filteredArticles = when (category) {
            Category.ALL -> articlesList
            else -> articlesList.filter { it.categoryId == category.id }
        }
        return Result.success(filteredArticles)
    }

    override suspend fun searchArticles(query: String): Result<List<Article>> {
        val filteredArticles =
            articlesList.filter { it.title.contains(query.trim(), ignoreCase = true) }
        return Result.success(filteredArticles)
    }

    override suspend fun getArticleDetails(id: Long): Result<ArticleDetails> {
        val articleDetails = articlesDetails.firstOrNull { it.id == id }
        return if (articleDetails != null) {
            Result.success(articleDetails)
        } else {
            Result.failure(IllegalArgumentException("Article with id $id not found"))
        }
    }

    override suspend fun getCategories(): Result<List<Category>> {
        val json = AssetUtils.readAssetFile(context, AssetUtils.CATEGORIES_FILE)
        val categoryListType = object : TypeToken<List<Category>>() {}.type
        return Result.success(gson.fromJson(json, categoryListType))
    }

    private fun loadArticles(): List<ArticleDetails> {
        val json = AssetUtils.readAssetFile(context, AssetUtils.ARTICLES_FILE)
        val articleListType = object : TypeToken<List<ArticleDetails>>() {}.type
        return gson.fromJson(json, articleListType)
    }
}