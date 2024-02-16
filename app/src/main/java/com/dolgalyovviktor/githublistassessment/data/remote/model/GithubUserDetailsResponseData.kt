package com.dolgalyovviktor.githublistassessment.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubUserDetailsResponseData(
    @SerialName("id") val id: Int,
    @SerialName("login") val login: String,
    @SerialName("avatar_url") val avatarUrl: String,
    @SerialName("name") val name: String?,
    @SerialName("company") val company: String?,
    @SerialName("location") val location: String?,
    @SerialName("email") val email: String?,
    @SerialName("bio") val bio: String?,
    @SerialName("followers") val followers: Int,
    @SerialName("following") val following: Int
)