package com.comppot.mindsnack.core.presentation.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

object SharingUtils {
    fun shareText(context: Context, text: String) {
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(context, shareIntent, null)
    }
}
