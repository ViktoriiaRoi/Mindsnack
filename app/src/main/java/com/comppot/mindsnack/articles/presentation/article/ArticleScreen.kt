package com.comppot.mindsnack.articles.presentation.article

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comppot.mindsnack.articles.domain.model.ArticleDetails
import com.comppot.mindsnack.articles.presentation.components.ArticleDetailsHeader
import com.comppot.mindsnack.articles.presentation.components.CardItem
import com.comppot.mindsnack.articles.presentation.components.RatingCard
import com.comppot.mindsnack.core.presentation.components.ArticleBottomBar
import com.comppot.mindsnack.core.presentation.components.CustomSnackBar
import com.comppot.mindsnack.core.presentation.components.ErrorMessage
import com.comppot.mindsnack.core.presentation.components.FullScreenLoading
import com.comppot.mindsnack.core.presentation.utils.applyFocusAlpha
import com.comppot.mindsnack.ui.theme.MindSnackTheme

@Composable
fun ArticleScreen(
    articleId: Long,
    viewModel: ArticleViewModel = hiltViewModel(),
    navigateUp: () -> Unit = {},
) {
    val state = viewModel.articleState.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.fetchArticleDetails(articleId)
    }

    Scaffold(
        bottomBar = {
            ArticleBottomBar(
                isSaved = state.details?.isSaved ?: false,
                savedCount = state.details?.savedCount ?: 0,
                onSavedClick = viewModel::updateArticleSaved,
                onShareClick = viewModel::shareArticle,
                navigateUp = navigateUp
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        snackbarHost = { CustomSnackBar() }
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            ArticleStateHandler(state, viewModel)
        }
    }
}

@Composable
private fun ArticleStateHandler(state: ArticleState, viewModel: ArticleViewModel) {
    when {
        state.details != null -> ArticleDetailsList(state.details, state.isRatingShown, viewModel)
        state.error != null -> ErrorMessage(stringResource(state.error.messageId))
        else -> FullScreenLoading()
    }
}

@Composable
private fun ArticleDetailsList(
    articleDetails: ArticleDetails,
    isRatingShown: Boolean,
    viewModel: ArticleViewModel
) {
    val context = LocalContext.current
    val lazyListState = rememberLazyListState()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)

    val lastCardIndex = articleDetails.numberOfCards - 1
    var isArticleRead by remember { mutableStateOf(false) }

    fun isFocused(index: Int): Boolean {
        val currentIndex = lazyListState.firstVisibleItemIndex
        return index == currentIndex
    }

    TrackVisibleItem(lazyListState) { visibleIndex ->
        if (visibleIndex == lastCardIndex && !isArticleRead) {
            isArticleRead = true
            viewModel.readArticle()
        }
    }

    BoxWithConstraints {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(48.dp),
            state = lazyListState,
            flingBehavior = snapBehavior
        ) {
            item {
                ArticleDetailsHeader(articleDetails, modifier = Modifier.padding(vertical = 64.dp))
            }
            itemsIndexed(articleDetails.cards) { index, card ->
                val cardContent: @Composable () -> Unit = {
                    CardItem(
                        card = card,
                        progressText = "${index + 1}/${articleDetails.numberOfCards}",
                        onSaveChanged = { viewModel.updateCardSaved(card.id, it) },
                        onShare = { viewModel.shareCard(context, card) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .applyFocusAlpha(isFocused(index))
                    )
                }
                if (!isRatingShown && index == lastCardIndex) {
                    PlacedInCenter(maxHeight) { cardContent() }
                } else {
                    cardContent()
                }
            }
            item {
                if (isRatingShown) {
                    PlacedInCenter(maxHeight) {
                        RatingCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .applyFocusAlpha(isFocused(lastCardIndex + 1)),
                            onSubmit = viewModel::saveRating
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TrackVisibleItem(lazyListState: LazyListState, onItemChanged: (Int) -> Unit) {
    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .collect { visibleIndex ->
                onItemChanged(visibleIndex)
            }
    }
}

@Composable
private fun PlacedInCenter(maxHeight: Dp, content: @Composable () -> Unit) {
    Layout(content = {
        content()
    }, measurePolicy = { measurables, constraints ->
        val placeable = measurables.first().measure(constraints)
        val maxHeightInPx = maxHeight.roundToPx()
        val itemHeight = placeable.height
        val yOffset = (maxHeightInPx - itemHeight) / 2
        val height = placeable.height + yOffset
        layout(placeable.width, height) {
            placeable.place(0, 0)
        }
    })
}

@Preview
@Composable
private fun ArticleScreenPreview() {
    MindSnackTheme {
        ArticleScreen(1)
    }
}
