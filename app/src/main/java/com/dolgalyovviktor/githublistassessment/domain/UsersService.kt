package com.dolgalyovviktor.githublistassessment.domain

import com.dolgalyovviktor.githublistassessment.data.UsersRepository
import com.dolgalyovviktor.githublistassessment.domain.model.UserDetails
import com.dolgalyovviktor.githublistassessment.domain.model.UserId
import com.dolgalyovviktor.githublistassessment.domain.model.UserListRequest
import com.dolgalyovviktor.githublistassessment.domain.model.UserListResponse

class UsersService(
    private val usersRepository: UsersRepository
) {
    suspend fun getUsers(
        since: UserId?,
        count: Int
    ): UserListResponse = usersRepository.getUsersList(
        request = UserListRequest(
            since = since,
            itemCount = count
        )
    )

    suspend fun getUser(username: String): UserDetails = usersRepository.getUser(username)
}