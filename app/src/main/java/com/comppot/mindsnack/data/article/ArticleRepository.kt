package com.comppot.mindsnack.data.article

import com.comppot.mindsnack.model.Article
import com.comppot.mindsnack.model.ArticleDetails
import com.comppot.mindsnack.model.CardInfo
import com.comppot.mindsnack.model.Category

interface ArticleRepository {
    fun getAllArticles(): List<Article>
    fun getArticleById(id: Long): ArticleDetails
    fun getCategories(): List<Category>
}

class ArticleRepositoryImpl : ArticleRepository {
    override fun getAllArticles() = List(7) { index ->
        Article(
            index.toLong(),
            "https://static.remove.bg/sample-gallery/graphics/bird-thumbnail.jpg",
            "How to make beautiful design using physics",
            1716226826,
            5,
            1
        )
    }

    override fun getArticleById(id: Long) = ArticleDetails(
        id,
        "https://static.remove.bg/sample-gallery/graphics/bird-thumbnail.jpg",
        "How to make beautiful design using physics",
        1716226826,
        5,
        7,
        0,
        List(7) { index ->
            CardInfo(
                index.toLong(),
                "Newton's second law",
                "The acceleration that a body acquires due to the action of a force is directly proportional to this force and inversely proportional to the mass of the body.\n\nThe object referred to in Newton's second law is a material point that has an inherent property of inertia, the magnitude of which is characterized by mass."
            )
        }
    )

    override fun getCategories() = listOf(
        Category(1, "Science"),
        Category(2, "Psychology"),
        Category(3, "Music"),
        Category(4, "Space"),
        Category(5, "Movies")
    )
}
