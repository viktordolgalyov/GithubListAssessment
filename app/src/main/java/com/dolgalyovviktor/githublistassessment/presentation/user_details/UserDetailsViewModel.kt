package com.dolgalyovviktor.githublistassessment.presentation.user_details

import androidx.lifecycle.viewModelScope
import com.dolgalyovviktor.githublistassessment.app.navigation.Navigator
import com.dolgalyovviktor.githublistassessment.common.arch.AppDispatchers
import com.dolgalyovviktor.githublistassessment.common.arch.ErrorHandler
import com.dolgalyovviktor.githublistassessment.common.arch.ExtraDataState
import com.dolgalyovviktor.githublistassessment.common.arch.FluxViewModel
import com.dolgalyovviktor.githublistassessment.domain.UsersService
import kotlinx.coroutines.launch

class UserDetailsViewModel(
    private val usersService: UsersService,
    private val username: String,
    private val navigator: Navigator,
    reducer: UserDetailsReducer,
    mapper: UserDetailsStateModelMapper,
    dispatchers: AppDispatchers
) : FluxViewModel<UserDetailsAction, UserDetailsChange, UserDetailsState, UserDetailsPresentationModel>(
    initialState = UserDetailsState.EMPTY,
    reducer = reducer,
    stateToModelMapper = mapper,
    dispatchers = dispatchers
) {
    override val errorHandler: ErrorHandler by lazy {
        { sendChange(UserDetailsChange.UserExtraDataStateChanged(ExtraDataState.Error)) }
    }

    init {
        loadUserDetails()
    }

    override fun processAction(action: UserDetailsAction) = when (action) {
        UserDetailsAction.BackClick -> navigator.back()
        UserDetailsAction.RetryClick -> loadUserDetails()
    }

    private fun loadUserDetails() {
        if (state.extraDataState == ExtraDataState.Loading) return
        viewModelScope.launch(dispatchers.subscribe) {
            sendChange(UserDetailsChange.UserExtraDataStateChanged(state = ExtraDataState.Loading))

            runCatching { usersService.getUser(username) }
                .onSuccess { sendChange(UserDetailsChange.UserDataLoaded(it)) }
                .onFailure(::onError)
        }
    }
}