package com.dolgalyovviktor.githublistassessment.domain.model

data class UserDetails(
    val id: UserId,
    val username: String,
    val avatarUrl: String,
    val company: String,
    val location: String,
    val bio: String,
    val email: String,
    val followersCount: Int,
    val followingCount: Int
)