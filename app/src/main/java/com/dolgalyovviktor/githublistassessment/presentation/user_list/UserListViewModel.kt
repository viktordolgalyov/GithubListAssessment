package com.dolgalyovviktor.githublistassessment.presentation.user_list

import androidx.lifecycle.viewModelScope
import com.dolgalyovviktor.githublistassessment.app.navigation.AppNavigator
import com.dolgalyovviktor.githublistassessment.app.navigation.Route
import com.dolgalyovviktor.githublistassessment.common.arch.AppDispatchers
import com.dolgalyovviktor.githublistassessment.common.arch.ErrorHandler
import com.dolgalyovviktor.githublistassessment.common.arch.ExtraDataState
import com.dolgalyovviktor.githublistassessment.common.arch.FluxViewModel
import com.dolgalyovviktor.githublistassessment.domain.UsersService
import kotlinx.coroutines.launch

private const val USERS_PER_PAGE = 10

class UserListViewModel(
    private val usersService: UsersService,
    private val navigator: AppNavigator,
    reducer: UserListReducer,
    mapper: UserListStateModelMapper,
    dispatchers: AppDispatchers
) : FluxViewModel<UserListAction, UserListChange, UserListState, UserListPresentationModel>(
    initialState = UserListState.EMPTY,
    reducer = reducer,
    stateToModelMapper = mapper,
    dispatchers = dispatchers
) {
    override val errorHandler: ErrorHandler by lazy {
        { sendChange(UserListChange.UsersExtraDataStateChanged(ExtraDataState.Error)) }
    }

    init {
        loadUsersPage()
    }

    override fun processAction(action: UserListAction) = when (action) {
        UserListAction.LoadMore -> loadUsersPage()
        is UserListAction.UserClick -> state.findUser(action.id)?.let { user ->
            navigator.forward(path = Route.UserDetails.createPath(user.username))
        }
    }

    private fun loadUsersPage() {
        if (state.usersExtraState == ExtraDataState.Loading) return

        viewModelScope.launch(dispatchers.subscribe) {
            sendChange(UserListChange.UsersExtraDataStateChanged(ExtraDataState.Loading))

            val since = state.userPages
                .flatMap { it.data }
                .maxByOrNull { it.id.data }
                ?.id

            runCatching { usersService.getUsers(since, USERS_PER_PAGE) }
                .onSuccess { sendChange(UserListChange.UsersPageLoaded(it)) }
                .onFailure { onError(it) }
        }
    }
}