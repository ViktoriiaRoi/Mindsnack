package com.comppot.mindsnack.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comppot.mindsnack.R
import com.comppot.mindsnack.core.common.CustomException

@Composable
fun ErrorMessage(error: CustomException, onRetry: (() -> Unit)? = null) {
    ErrorMessage(stringResource(error.messageId), onRetry)
}

@Composable
fun ErrorMessage(message: String, onRetry: (() -> Unit)? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            message,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        if (onRetry != null) {
            RetryButton(onRetry)
        }
    }
}

@Composable
private fun RetryButton(onClick: () -> Unit) {
    Button(onClick) {
        Text(stringResource(R.string.error_message_retry))
    }
}

@Preview
@Composable
private fun ErrorMessagePreview() {
    ErrorMessage(CustomException.UnknownError)
}
