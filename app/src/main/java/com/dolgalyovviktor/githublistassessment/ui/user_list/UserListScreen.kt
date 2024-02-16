package com.dolgalyovviktor.githublistassessment.ui.user_list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dolgalyovviktor.githublistassessment.R
import com.dolgalyovviktor.githublistassessment.common.res.Dimens
import com.dolgalyovviktor.githublistassessment.common.ui.LazyColumnLoadMore
import com.dolgalyovviktor.githublistassessment.common.ui.LazyGridLoadMore
import com.dolgalyovviktor.githublistassessment.common.ui.disableSplitMotionEvents
import com.dolgalyovviktor.githublistassessment.domain.model.UserId
import com.dolgalyovviktor.githublistassessment.presentation.user_list.UserListAction
import com.dolgalyovviktor.githublistassessment.presentation.user_list.UserListItem
import com.dolgalyovviktor.githublistassessment.presentation.user_list.UserListPresentationModel
import com.dolgalyovviktor.githublistassessment.presentation.user_list.UserListViewModel
import org.koin.androidx.compose.getViewModel

private const val GRID_COLUMN_COUNT = 4

@Composable
fun UserListScreen(
    modifier: Modifier = Modifier,
    viewModel: UserListViewModel = getViewModel()
) {
    Scaffold(topBar = { UserListAppBar() }) { paddingValues ->
        UserListContent(
            model = viewModel.model.collectAsState().value,
            onUserClick = { userId ->
                viewModel.dispatch(UserListAction.UserClick(userId))
            },
            onLoadMoreTriggered = {
                viewModel.dispatch(UserListAction.LoadMore)
            },
            onRetryClick = {
                viewModel.dispatch(UserListAction.LoadMore)
            },
            modifier = modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun UserListContent(
    modifier: Modifier = Modifier,
    model: UserListPresentationModel?,
    onUserClick: (UserId) -> Unit,
    onLoadMoreTriggered: () -> Unit,
    onRetryClick: () -> Unit
) = UserListItemsView(
    data = model?.items.orEmpty(),
    onUserClick = onUserClick,
    modifier = modifier,
    onLoadMoreTriggered = onLoadMoreTriggered,
    onRetryClick = onRetryClick
)

@Composable
private fun UserListAppBar() = TopAppBar(
    title = {
        Text(
            text = stringResource(id = R.string.title_user_list),
            style = MaterialTheme.typography.titleLarge
        )
    }
)

@Composable
private fun UserListItemsView(
    modifier: Modifier = Modifier,
    data: List<UserListItem>,
    onUserClick: (UserId) -> Unit,
    onLoadMoreTriggered: () -> Unit,
    onRetryClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val contentPadding = remember {
        PaddingValues(
            horizontal = Dimens.padding,
            vertical = Dimens.halfPadding
        )
    }
    val itemSpaceArrangement = remember { Arrangement.spacedBy(Dimens.quarterPadding) }

    if (isPortrait) {
        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            contentPadding = contentPadding,
            verticalArrangement = itemSpaceArrangement,
            modifier = modifier.disableSplitMotionEvents()
        ) {
            items(
                items = data,
                contentType = { it.contentType },
                key = { it.key },
                itemContent = {
                    UserListItemComposable(
                        item = it,
                        onUserClick = onUserClick,
                        onRetryClick = onRetryClick
                    )
                }
            )
        }
        LazyColumnLoadMore(
            listState = listState,
            dataSizeProvider = { data.size },
            loadingStateProvider = { data.any { it is UserListItem.Progress } },
            onLoadMore = onLoadMoreTriggered
        )
    } else {
        val gridState = rememberLazyGridState()
        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Fixed(GRID_COLUMN_COUNT),
            contentPadding = contentPadding,
            modifier = modifier.disableSplitMotionEvents(),
            verticalArrangement = itemSpaceArrangement,
            horizontalArrangement = itemSpaceArrangement
        ) {
            items(
                items = data,
                key = { it.key },
                contentType = { it.contentType },
                itemContent = {
                    UserListItemComposable(
                        item = it,
                        onUserClick = onUserClick,
                        onRetryClick = onRetryClick
                    )
                },
            )
        }
        LazyGridLoadMore(
            gridState = gridState,
            dataSizeProvider = { data.size },
            loadingStateProvider = { data.any { it is UserListItem.Progress } },
            onLoadMore = onLoadMoreTriggered
        )
    }
}

@Composable
private fun UserListItemComposable(
    item: UserListItem,
    onUserClick: (UserId) -> Unit,
    onRetryClick: () -> Unit
) = when (item) {
    UserListItem.Progress -> UserListItemProgressView()
    is UserListItem.Retry -> UserListItemRetryView(onRetryClick = onRetryClick)
    is UserListItem.User -> UserListItemView(
        model = item.model,
        onClick = { userId -> onUserClick(userId) }
    )
}

@Preview(showBackground = true)
@Composable
private fun UserListContentPreview() = UserListContent(
    model = UserListPresentationModel(
        items = (0..5).map {
            UserListItem.User(
                model = UserRenderModel(
                    id = UserId(it),
                    username = "Username $it",
                    description = "Description",
                    avatarUrl = ""
                )
            )
        } + UserListItem.Progress + UserListItem.Retry
    ),
    onUserClick = {},
    onLoadMoreTriggered = {},
    onRetryClick = {}
)