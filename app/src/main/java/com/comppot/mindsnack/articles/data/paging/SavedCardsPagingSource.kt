package com.comppot.mindsnack.articles.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.comppot.mindsnack.articles.data.remote.SavingApi
import com.comppot.mindsnack.articles.data.remote.dto.toSavedCard
import com.comppot.mindsnack.articles.domain.model.SavedCard
import com.comppot.mindsnack.core.data.utils.runSafe

class SavedCardsPagingSource(val api: SavingApi) : PagingSource<Int, SavedCard>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SavedCard> {
        val currentPage = params.key ?: 1
        val result = runSafe { api.getSavedCards(currentPage) }
        return result.fold({ page ->
            LoadResult.Page(
                data = page.objects.map { it.toSavedCard() },
                prevKey = null,
                nextKey = if (page.hasNext) currentPage + 1 else null
            )
        }, { error -> LoadResult.Error(error) })
    }

    override fun getRefreshKey(state: PagingState<Int, SavedCard>): Int = 1
}
