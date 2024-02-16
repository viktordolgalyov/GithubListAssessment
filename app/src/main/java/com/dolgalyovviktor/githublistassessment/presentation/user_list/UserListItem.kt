package com.dolgalyovviktor.githublistassessment.presentation.user_list

import com.dolgalyovviktor.githublistassessment.ui.user_list.UserRenderModel

sealed class UserListItem {
    val contentType: String
        get() = this::class.java.name
    val key: String
        get() = when (this) {
            is User -> this.model.id.data.toString()
            Progress -> Progress.toString()
            Retry -> Retry.toString()
        }

    data class User(val model: UserRenderModel) : UserListItem()
    data object Progress : UserListItem()
    data object Retry : UserListItem()
}