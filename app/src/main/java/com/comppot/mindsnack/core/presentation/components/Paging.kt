@file:OptIn(ExperimentalMaterial3Api::class)

package com.comppot.mindsnack.core.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.comppot.mindsnack.core.common.CustomException

fun <T : Any> LazyPagingItems<T>.isEmpty(): Boolean {
    return itemCount == 0 && loadState.isIdle
}

@Composable
fun <T : Any> PagingBox(
    items: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    emptyMessage: String = "",
    content: @Composable () -> Unit
) {
    val refreshState = items.loadState.refresh
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(refreshState) {
        if (isRefreshing && refreshState !is LoadState.Loading) {
            isRefreshing = false
        }
    }

    PullToRefreshBox(
        isRefreshing = refreshState is LoadState.Loading && isRefreshing,
        onRefresh = {
            items.refresh()
            isRefreshing = true
        },
        modifier = modifier
    ) {
        content()
        when {
            refreshState is LoadState.Loading && !isRefreshing -> FullScreenLoading()
            refreshState is LoadState.Error -> ErrorMessage(
                refreshState.error as CustomException,
                onRetry = items::retry
            )
            items.isEmpty() -> ErrorMessage(emptyMessage)
        }
    }
}

@Composable
fun <T : Any> AppendStateHandler(items: LazyPagingItems<T>) {
    when (val state = items.loadState.append) {
        is LoadState.Loading -> FullScreenLoading()
        is LoadState.Error -> ErrorMessage(state.error as CustomException, items::retry)
        else -> {}
    }
}
