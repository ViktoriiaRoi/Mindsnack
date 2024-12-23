package com.comppot.mindsnack.core.presentation.utils

import androidx.annotation.StringRes
import com.comppot.mindsnack.core.common.CustomException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object SnackbarController {
    private val _events = Channel<SnackbarMessage>()
    val events = _events.receiveAsFlow()

    suspend fun showMessage(@StringRes resId: Int) {
        _events.send(SnackbarMessage(resId))
    }

    suspend fun showErrorMessage(error: Throwable) {
        val customException = error as? CustomException ?: return
        _events.send(SnackbarMessage(customException.messageId))
    }
}

data class SnackbarMessage(
    @StringRes val resId: Int
)
