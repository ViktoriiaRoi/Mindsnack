package com.comppot.mindsnack.data.utils

import android.content.Context

object AssetUtils {
    const val CATEGORIES_FILE = "categories.json"
    const val ARTICLES_FILE = "articles.json"

    fun readAssetFile(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}