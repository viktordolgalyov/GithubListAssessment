package com.dolgalyovviktor.githublistassessment.presentation.user_list

import com.dolgalyovviktor.githublistassessment.common.arch.ExtraDataState
import com.dolgalyovviktor.githublistassessment.common.arch.Reducer

class UserListReducer : Reducer<UserListState, UserListChange> {

    override fun reduce(
        state: UserListState,
        change: UserListChange
    ): UserListState = when (change) {
        is UserListChange.UsersExtraDataStateChanged -> state.copy(
            usersExtraState = change.state
        )

        is UserListChange.UsersPageLoaded -> state.copy(
            userPages = (state.userPages + change.data).distinct(),
            usersExtraState = ExtraDataState.None
        )
    }
}