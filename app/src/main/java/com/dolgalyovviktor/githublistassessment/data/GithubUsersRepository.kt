package com.dolgalyovviktor.githublistassessment.data

import com.dolgalyovviktor.githublistassessment.data.local.user.UsersLocalSource
import com.dolgalyovviktor.githublistassessment.data.remote.GithubUsersRemoteSource
import com.dolgalyovviktor.githublistassessment.domain.model.UserDetails
import com.dolgalyovviktor.githublistassessment.domain.model.UserListRequest
import com.dolgalyovviktor.githublistassessment.domain.model.UserListResponse

class GithubUsersRepository(
    private val localSource: UsersLocalSource,
    private val remoteSource: GithubUsersRemoteSource
) : UsersRepository {

    override suspend fun getUsersList(request: UserListRequest): UserListResponse {
        val cached = localSource.getUserList(request)
        if (cached != null) return cached
        return remoteSource.getUsers(request).also {
            localSource.storeUserListResponse(it)
        }
    }

    override suspend fun getUser(username: String): UserDetails {
        val cached = localSource.getUser(username)
        if (cached != null) return cached
        return remoteSource.getUser(username).also { remoteUser ->
            localSource.storeUser(remoteUser)
        }
    }
}