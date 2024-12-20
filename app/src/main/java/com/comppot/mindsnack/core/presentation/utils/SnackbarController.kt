package com.comppot.mindsnack.core.presentation.utils

import androidx.annotation.StringRes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object SnackbarController {
    private val _events = Channel<SnackbarMessage>()
    val events = _events.receiveAsFlow()

    suspend fun showMessage(@StringRes resId: Int) {
        _events.send(SnackbarMessage(resId))
    }
}

data class SnackbarMessage(
    @StringRes val resId: Int
)
