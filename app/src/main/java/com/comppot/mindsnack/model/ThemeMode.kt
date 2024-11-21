package com.comppot.mindsnack.model

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.comppot.mindsnack.R

enum class ThemeMode(val id: Int, val stringId: Int, val icon: ImageVector) {
    AUTO(0, R.string.dialog_theme_mode_auto, Icons.Default.Contrast),
    LIGHT(1, R.string.dialog_theme_mode_light, Icons.Default.LightMode),
    DARK(2, R.string.dialog_theme_mode_dark, Icons.Default.DarkMode);

    @Composable
    fun isDarkMode() = when (this) {
        LIGHT -> false
        DARK -> true
        else -> isSystemInDarkTheme()
    }

    companion object {
        fun fromId(id: Int?): ThemeMode {
            return entries.find { it.id == id } ?: AUTO
        }
    }
}
