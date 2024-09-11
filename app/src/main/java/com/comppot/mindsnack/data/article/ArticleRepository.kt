package com.comppot.mindsnack.data.article

import android.content.Context
import com.comppot.mindsnack.model.Article
import com.comppot.mindsnack.model.ArticleDetails
import com.comppot.mindsnack.model.Category
import com.comppot.mindsnack.data.utils.AssetUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

interface ArticleRepository {
    fun getAllArticles(): List<Article>
    fun getArticleById(id: Long): ArticleDetails
    fun getCategories(): List<Category>
}

class ArticleRepositoryImpl @Inject constructor(
    private val context: Context,
    private val gson: Gson
) : ArticleRepository {

    private val articles: List<ArticleDetails> by lazy {
        loadArticles()
    }

    override fun getAllArticles(): List<Article> {
        return articles.map {
            Article(
                id = it.id,
                image = it.image,
                title = it.title,
                postDate = it.postDate,
                numberOfCards = it.numberOfCards,
                categoryId = it.categoryId
            )
        }
    }

    override fun getArticleById(id: Long): ArticleDetails {
        return articles.firstOrNull { it.id == id }
            ?: throw IllegalArgumentException("Article with id $id not found")
    }

    override fun getCategories(): List<Category> {
        val json = AssetUtils.readAssetFile(context, AssetUtils.CATEGORIES_FILE)
        val categoryListType = object : TypeToken<List<Category>>() {}.type
        return gson.fromJson(json, categoryListType)
    }

    private fun loadArticles(): List<ArticleDetails> {
        val json = AssetUtils.readAssetFile(context, AssetUtils.ARTICLES_FILE)
        val articleListType = object : TypeToken<List<ArticleDetails>>() {}.type
        return gson.fromJson(json, articleListType)
    }
}
