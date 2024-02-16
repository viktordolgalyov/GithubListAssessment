package com.dolgalyovviktor.githublistassessment.domain.model

data class User(
    val id: UserId,
    val username: String,
    val email: String,
    val avatarUrl: String
)