package com.dolgalyovviktor.githublistassessment.presentation.user_details

import com.dolgalyovviktor.githublistassessment.common.arch.ExtraDataState
import com.dolgalyovviktor.githublistassessment.common.arch.UIState
import com.dolgalyovviktor.githublistassessment.domain.model.UserDetails

data class UserDetailsState(
    val user: UserDetails?,
    val extraDataState: ExtraDataState
) : UIState {
    companion object {
        val EMPTY = UserDetailsState(
            user = null,
            extraDataState = ExtraDataState.None
        )
    }
}