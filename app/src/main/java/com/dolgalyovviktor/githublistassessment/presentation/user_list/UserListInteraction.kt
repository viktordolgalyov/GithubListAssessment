package com.dolgalyovviktor.githublistassessment.presentation.user_list

import com.dolgalyovviktor.githublistassessment.common.arch.ExtraDataState
import com.dolgalyovviktor.githublistassessment.common.arch.UIAction
import com.dolgalyovviktor.githublistassessment.common.arch.UIStateChange
import com.dolgalyovviktor.githublistassessment.domain.model.UserId
import com.dolgalyovviktor.githublistassessment.domain.model.UserListResponse

sealed class UserListAction : UIAction {
    data class UserClick(val id: UserId) : UserListAction()
    data object LoadMore : UserListAction()
}

sealed class UserListChange : UIStateChange {
    data class UsersExtraDataStateChanged(val state: ExtraDataState) : UserListChange()
    data class UsersPageLoaded(val data: UserListResponse) : UserListChange()
}