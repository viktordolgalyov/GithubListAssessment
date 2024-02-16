package com.dolgalyovviktor.githublistassessment.data.remote

import com.dolgalyovviktor.githublistassessment.BuildConfig
import com.dolgalyovviktor.githublistassessment.data.remote.model.GithubUserDetailsResponseData
import com.dolgalyovviktor.githublistassessment.data.remote.model.GithubUserResponseData
import com.dolgalyovviktor.githublistassessment.domain.model.User
import com.dolgalyovviktor.githublistassessment.domain.model.UserDetails
import com.dolgalyovviktor.githublistassessment.domain.model.UserId
import com.dolgalyovviktor.githublistassessment.domain.model.UserListRequest
import com.dolgalyovviktor.githublistassessment.domain.model.UserListResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class GithubUsersRemoteSource(private val client: HttpClient) {
    suspend fun getUsers(request: UserListRequest): UserListResponse {
        val url = "${BuildConfig.API_BASE_URL}users"
        val requestResult = client.get(url) {
            if (request.since != null) parameter("since", request.since.data)
            parameter("per_page", request.itemCount)
        }
        val response = requestResult.body<List<GithubUserResponseData>>()

        return UserListResponse(
            data = response.map { it.convert() }
        )
    }

    suspend fun getUser(username: String): UserDetails {
        val url = "${BuildConfig.API_BASE_URL}users/$username"
        val requestResult = client.get(url)

        val response = requestResult.body<GithubUserDetailsResponseData>()

        return response.convert()
    }
}

private fun GithubUserResponseData.convert() = User(
    id = UserId(this.id),
    username = this.login,
    email = this.email.orEmpty(),
    avatarUrl = this.avatarUrl
)

fun GithubUserDetailsResponseData.convert(): UserDetails {
    return UserDetails(
        id = UserId(this.id),
        username = this.login,
        avatarUrl = this.avatarUrl,
        company = this.company.orEmpty(),
        location = this.location.orEmpty(),
        bio = this.bio.orEmpty(),
        email = this.email.orEmpty(),
        followersCount = this.followers,
        followingCount = this.following
    )
}