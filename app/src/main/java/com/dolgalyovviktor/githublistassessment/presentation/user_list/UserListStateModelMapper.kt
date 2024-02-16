package com.dolgalyovviktor.githublistassessment.presentation.user_list

import com.dolgalyovviktor.githublistassessment.common.arch.ExtraDataState
import com.dolgalyovviktor.githublistassessment.common.arch.StateToModelMapper
import com.dolgalyovviktor.githublistassessment.domain.model.User
import com.dolgalyovviktor.githublistassessment.ui.user_list.UserRenderModel

class UserListStateModelMapper : StateToModelMapper<UserListState, UserListPresentationModel> {

    override fun mapStateToModel(state: UserListState): UserListPresentationModel {
        val errorItem = listOfNotNull(
            UserListItem.Retry.takeIf { state.usersExtraState == ExtraDataState.Error }
        )
        val progressItem = listOfNotNull(
            UserListItem.Progress.takeIf { state.usersExtraState == ExtraDataState.Loading }
        )
        val userItems = state.userPages
            .flatMap { page -> page.data }
            .map { UserListItem.User(it.asRenderModel()) }

        return UserListPresentationModel(
            items = userItems + progressItem + errorItem
        )
    }
}

private fun User.asRenderModel() = UserRenderModel(
    id = this.id,
    username = this.username,
    avatarUrl = this.avatarUrl,
    description = this.email
)