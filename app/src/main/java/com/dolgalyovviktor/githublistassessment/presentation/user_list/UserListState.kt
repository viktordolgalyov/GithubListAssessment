package com.dolgalyovviktor.githublistassessment.presentation.user_list

import com.dolgalyovviktor.githublistassessment.common.arch.ExtraDataState
import com.dolgalyovviktor.githublistassessment.common.arch.UIState
import com.dolgalyovviktor.githublistassessment.domain.model.UserId
import com.dolgalyovviktor.githublistassessment.domain.model.UserListResponse

data class UserListState(
    val userPages: List<UserListResponse>,
    val usersExtraState: ExtraDataState
) : UIState {
    companion object {
        val EMPTY = UserListState(
            userPages = emptyList(),
            usersExtraState = ExtraDataState.None
        )
    }

    fun findUser(id: UserId) = userPages
        .flatMap { page -> page.data }
        .find { user -> user.id == id }
}