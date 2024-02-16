package com.dolgalyovviktor.githublistassessment.domain.model

data class UserListRequest(
    val since: UserId?,
    val itemCount: Int
)