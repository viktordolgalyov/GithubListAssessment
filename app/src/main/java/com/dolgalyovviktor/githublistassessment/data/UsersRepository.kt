package com.dolgalyovviktor.githublistassessment.data

import com.dolgalyovviktor.githublistassessment.domain.model.UserDetails
import com.dolgalyovviktor.githublistassessment.domain.model.UserListRequest
import com.dolgalyovviktor.githublistassessment.domain.model.UserListResponse

interface UsersRepository {
    suspend fun getUsersList(request: UserListRequest): UserListResponse
    suspend fun getUser(username: String): UserDetails
}