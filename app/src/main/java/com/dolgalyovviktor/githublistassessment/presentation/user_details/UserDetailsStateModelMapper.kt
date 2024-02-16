package com.dolgalyovviktor.githublistassessment.presentation.user_details

import com.dolgalyovviktor.githublistassessment.common.arch.ExtraDataState
import com.dolgalyovviktor.githublistassessment.common.arch.StateToModelMapper

class UserDetailsStateModelMapper :
    StateToModelMapper<UserDetailsState, UserDetailsPresentationModel> {

    override fun mapStateToModel(state: UserDetailsState): UserDetailsPresentationModel {
        return when (val user = state.user) {
            null -> when {
                state.extraDataState == ExtraDataState.Error -> UserDetailsPresentationModel.Error
                else -> UserDetailsPresentationModel.Loading
            }

            else -> UserDetailsPresentationModel.User(
                username = user.username,
                avatarUrl = user.avatarUrl,
                company = user.company,
                location = user.location,
                bio = user.bio,
                email = user.email,
                followersCount = user.followersCount,
                followingCount = user.followingCount
            )
        }
    }
}