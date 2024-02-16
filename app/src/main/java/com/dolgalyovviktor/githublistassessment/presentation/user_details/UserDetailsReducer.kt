package com.dolgalyovviktor.githublistassessment.presentation.user_details

import com.dolgalyovviktor.githublistassessment.common.arch.ExtraDataState
import com.dolgalyovviktor.githublistassessment.common.arch.Reducer

class UserDetailsReducer : Reducer<UserDetailsState, UserDetailsChange> {
    override fun reduce(
        state: UserDetailsState,
        change: UserDetailsChange
    ): UserDetailsState = when (change) {
        is UserDetailsChange.UserDataLoaded -> state.copy(
            user = change.data,
            extraDataState = ExtraDataState.None
        )

        is UserDetailsChange.UserExtraDataStateChanged -> state.copy(
            extraDataState = change.state
        )
    }
}