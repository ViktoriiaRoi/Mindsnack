package com.comppot.mindsnack.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun MindSnackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme
            .adjustForDarkTheme(darkTheme)
            .adjustPrimaryContainer(darkTheme),
        shapes = Shapes,
        typography = Typography,
        content = content
    )
}

private fun ColorScheme.adjustForDarkTheme(darkTheme: Boolean): ColorScheme =
    if (darkTheme) copy(
        surface = this.surfaceContainerHigh,
        surfaceContainer = this.surface
    ) else this

private fun ColorScheme.adjustPrimaryContainer(darkTheme: Boolean): ColorScheme {
    val isTooDark = !darkTheme && primaryContainer.luminance() < 0.2f
    val isTooLight = darkTheme && primaryContainer.luminance() > 0.6f
    return if (isTooDark || isTooLight) {
        copy(primaryContainer = primaryContainer.copy(alpha = 0.25f))
    } else this
}
