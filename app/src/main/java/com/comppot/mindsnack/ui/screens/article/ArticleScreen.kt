package com.comppot.mindsnack.ui.screens.article

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comppot.mindsnack.model.ArticleDetails
import com.comppot.mindsnack.model.CardInfo
import com.comppot.mindsnack.model.Rating
import com.comppot.mindsnack.ui.components.ArticleBottomBar
import com.comppot.mindsnack.ui.components.ArticleDetailsHeader
import com.comppot.mindsnack.ui.components.CustomSnackBar
import com.comppot.mindsnack.ui.components.FullScreenLoading
import com.comppot.mindsnack.ui.theme.MindSnackTheme
import com.comppot.mindsnack.ui.utils.applyFocusAlpha

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
                isSaved = state.isSaved,
                savedCount = state.savedCount,
                onSavedClick = viewModel::updateArticleSaved,
                onShareClick = viewModel::shareArticle,
                navigateUp = navigateUp
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        snackbarHost = { CustomSnackBar() }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when {
                state.isLoading -> FullScreenLoading()
                state.articleDetails != null -> ArticleDetailsList(
                    state.articleDetails,
                    state.isRatingShown,
                    viewModel::saveRating
                )
            }
        }
    }
}

@Composable
private fun ArticleDetailsList(
    articleDetails: ArticleDetails,
    isRatingShown: Boolean,
    saveRating: (Rating) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)

    fun isFocused(index: Int): Boolean {
        val currentIndex = lazyListState.firstVisibleItemIndex
        return index == currentIndex
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
                val progressText = "${index + 1}/${articleDetails.numberOfCards}"
                val modifier = Modifier
                    .fillMaxWidth()
                    .applyFocusAlpha(isFocused(index))
                if (!isRatingShown && index == articleDetails.numberOfCards - 1) {
                    PlacedInCenter(maxHeight) {
                        TextCard(card, progressText, modifier)
                    }
                } else {
                    TextCard(card, progressText, modifier)
                }
            }
            item {
                if (isRatingShown) {
                    PlacedInCenter(maxHeight) {
                        RatingCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .applyFocusAlpha(isFocused(articleDetails.numberOfCards)),
                            onSubmit = saveRating
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TextCard(
    card: CardInfo,
    progressText: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (!card.title.isNullOrEmpty()) {
                Text(
                    text = card.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Text(text = card.text, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = progressText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.5f)
            )
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
