package com.dolgalyovviktor.githublistassessment.common.ui

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull

private const val DEBOUNCE_PERIOD = 100L
private const val PREFETCH_DISTANCE = 10

@Composable
private fun <T> LoadMoreEffect(
    scrollState: T,
    getLastVisibleItemIndex: (T) -> Int?,
    dataSizeProvider: () -> Int,
    loadingStateProvider: () -> Boolean = { false },
    onLoadMore: () -> Unit
) {
    LaunchedEffect(scrollState) {
        snapshotFlow { getLastVisibleItemIndex(scrollState) }
            .filterNotNull()
            .filter { it >= 0 && !loadingStateProvider() }
            .debounce(DEBOUNCE_PERIOD)
            .collect { lastIndex ->
                val thresholdIndex = dataSizeProvider() - PREFETCH_DISTANCE - 1
                if (lastIndex >= thresholdIndex) onLoadMore()
            }
    }
}

@Composable
fun LazyColumnLoadMore(
    listState: LazyListState,
    dataSizeProvider: () -> Int,
    loadingStateProvider: () -> Boolean = { false },
    onLoadMore: () -> Unit
) = LoadMoreEffect(
    scrollState = listState,
    getLastVisibleItemIndex = { it.layoutInfo.visibleItemsInfo.lastOrNull()?.index },
    dataSizeProvider = dataSizeProvider,
    loadingStateProvider = loadingStateProvider,
    onLoadMore = onLoadMore
)

@Composable
fun LazyGridLoadMore(
    gridState: LazyGridState,
    dataSizeProvider: () -> Int,
    loadingStateProvider: () -> Boolean = { false },
    onLoadMore: () -> Unit
) = LoadMoreEffect(
    scrollState = gridState,
    getLastVisibleItemIndex = { it.layoutInfo.visibleItemsInfo.lastOrNull()?.index },
    dataSizeProvider = dataSizeProvider,
    loadingStateProvider = loadingStateProvider,
    onLoadMore = onLoadMore
)