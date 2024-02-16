package com.dolgalyovviktor.githublistassessment.presentation.user_details

import com.dolgalyovviktor.githublistassessment.common.arch.UIModel

sealed class UserDetailsPresentationModel : UIModel {
    data class User(
        val username: String,
        val avatarUrl: String,
        val company: String,
        val location: String,
        val bio: String,
        val email: String,
        val followersCount: Int,
        val followingCount: Int
    ) : UserDetailsPresentationModel()

    data object Loading : UserDetailsPresentationModel()

    data object Error : UserDetailsPresentationModel()
}