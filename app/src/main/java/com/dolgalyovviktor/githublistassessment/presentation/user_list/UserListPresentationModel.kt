package com.dolgalyovviktor.githublistassessment.presentation.user_list

import com.dolgalyovviktor.githublistassessment.common.arch.UIModel

data class UserListPresentationModel(
    val items: List<UserListItem>
) : UIModel