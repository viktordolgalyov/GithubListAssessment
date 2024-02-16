package com.dolgalyovviktor.githublistassessment.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubUserResponseData(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String?,
    @SerialName("email") val email: String?,
    @SerialName("login") val login: String,
    @SerialName("avatar_url") val avatarUrl: String
)