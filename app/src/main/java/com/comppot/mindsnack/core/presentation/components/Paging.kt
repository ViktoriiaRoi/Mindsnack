@file:OptIn(ExperimentalMaterial3Api::class)

package com.comppot.mindsnack.core.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.comppot.mindsnack.core.common.CustomException

fun <T : Any> LazyPagingItems<T>.isEmpty(): Boolean {
    return itemCount == 0 && loadState.isIdle
}

@Composable
fun <T : Any> PagingBox(items: LazyPagingItems<T>, content: @Composable () -> Unit) {
    PullToRefreshBox(
        isRefreshing = items.loadState.refresh is LoadState.Loading,
        onRefresh = { items.refresh() },
    ) {
        content()
    }
}

@Composable
fun RefreshStateHandler(state: LoadState, isEmpty: Boolean, emptyMessage: String = "") {
    when {
        state is LoadState.Error -> ErrorMessage(state.error as CustomException)
        isEmpty -> ErrorMessage(emptyMessage)
    }
}

@Composable
fun AppendStateHandler(state: LoadState) {
    when (state) {
        is LoadState.Loading -> FullScreenLoading()
        is LoadState.Error -> ErrorMessage(state.error as CustomException)
        else -> {}
    }
}
