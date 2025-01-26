package com.comppot.mindsnack.articles.presentation.saved

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.comppot.mindsnack.R
import com.comppot.mindsnack.articles.domain.model.SavedCard
import com.comppot.mindsnack.articles.presentation.components.SavedCardItem
import com.comppot.mindsnack.core.presentation.components.AppendStateHandler
import com.comppot.mindsnack.core.presentation.components.PagingBox

@Composable
fun CardsTab(cards: LazyPagingItems<SavedCard>, openArticle: (Long) -> Unit) {
    CardPagingList(cards, openArticle)
}

@Composable
private fun CardPagingList(cards: LazyPagingItems<SavedCard>, onCardClick: (Long) -> Unit) {
    PagingBox(cards, emptyMessage = stringResource(R.string.saved_screen_no_cards)) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
            modifier = Modifier.fillMaxSize()
        ) {
            items(cards.itemCount, key = cards.itemKey { it.details.id }) { index ->
                cards[index]?.let {
                    SavedCardItem(
                        card = it.details,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onCardClick(it.articleId) }
                    )
                }
            }
            item(span = StaggeredGridItemSpan.FullLine) {
                AppendStateHandler(cards)
            }
        }
    }
}
