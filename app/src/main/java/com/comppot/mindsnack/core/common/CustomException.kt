package com.comppot.mindsnack.core.common

import androidx.annotation.StringRes
import com.comppot.mindsnack.R

sealed class CustomException(@StringRes val messageId: Int) : Exception() {
    object NoInternetConnection : CustomException(R.string.error_no_internet)
    object ServerError : CustomException(R.string.error_server)
    object UnknownError : CustomException(R.string.error_unknown)
}
