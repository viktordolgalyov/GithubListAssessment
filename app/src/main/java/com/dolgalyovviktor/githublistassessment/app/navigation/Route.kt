package com.dolgalyovviktor.githublistassessment.app.navigation

sealed class Route(val path: String) {
    companion object {
        fun all() = listOf(UserList, UserDetails)
    }

    data object UserList : Route("users/list")

    data object UserDetails : Route("users/{${NavigationArg.Username.name}}") {
        fun createPath(username: String) = "users/$username"
    }
}