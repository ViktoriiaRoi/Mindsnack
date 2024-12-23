package com.comppot.mindsnack.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comppot.mindsnack.core.presentation.Status

@Composable
fun <T> StatusHandler(
    status: Status<T>,
    modifier: Modifier = Modifier,
    emptyMessage: String = "",
    onSuccess: @Composable (T) -> Unit = {}
) {
    Box(modifier) {
        when (status) {
            is Status.Loading -> FullScreenLoading()
            is Status.Empty -> ErrorMessage(emptyMessage)
            is Status.Error -> ErrorMessage(stringResource(status.error.messageId))
            is Status.Success -> onSuccess(status.data)
        }
    }
}

@Composable
fun FullScreenLoading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun ErrorMessage(message: String) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 32.dp)) {
        Text(
            message,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun FullScreenLoadingPreview() {
    FullScreenLoading()
}

@Preview
@Composable
fun ErrorMessagePreview() {
    ErrorMessage("Error")
}
