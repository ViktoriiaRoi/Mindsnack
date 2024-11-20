package com.comppot.mindsnack.ui.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color

fun Color.takeColorIf(condition: Boolean): Color = if (condition) this else Color.Transparent

fun Modifier.applyFocusAlpha(value: Boolean) = if (value) this else alpha(0.5f)
