package com.dolgalyovviktor.githublistassessment.data.local.user

import com.dolgalyovviktor.githublistassessment.data.local.user.entity.UserAndDetails
import com.dolgalyovviktor.githublistassessment.data.local.user.entity.UserDetailsEntity
import com.dolgalyovviktor.githublistassessment.data.local.user.entity.UserEntity
import com.dolgalyovviktor.githublistassessment.domain.model.User
import com.dolgalyovviktor.githublistassessment.domain.model.UserDetails
import com.dolgalyovviktor.githublistassessment.domain.model.UserId
import com.dolgalyovviktor.githublistassessment.domain.model.UserListRequest
import com.dolgalyovviktor.githublistassessment.domain.model.UserListResponse

class UsersLocalSource(private val usersDao: UsersDao) {

    suspend fun getUserList(request: UserListRequest): UserListResponse? {
        val users = usersDao.getUsers(
            sinceId = request.since?.data ?: 0,
            itemCount = request.itemCount
        )
        val userList = users
            .map { entity -> entity.asUser() }
            .takeIf { it.isNotEmpty() }
        return if (userList != null) UserListResponse(userList) else null
    }

    suspend fun getUser(username: String): UserDetails? {
        val userAndDetails = usersDao.getUserByUsername(username) ?: return null
        if (userAndDetails.details == null) return null
        return userAndDetails.convert()
    }

    suspend fun storeUserListResponse(response: UserListResponse) {
        val users = response.data.map { user -> user.asEntity() }
        usersDao.insertUsers(users)
    }

    suspend fun storeUser(userDetails: UserDetails) {
        val userEntity = UserEntity(
            userId = userDetails.id.data,
            username = userDetails.username,
            email = userDetails.email,
            avatarUrl = userDetails.avatarUrl
        )
        val userDetailsEntity = UserDetailsEntity(
            userId = userDetails.id.data,
            company = userDetails.company,
            location = userDetails.location,
            bio = userDetails.bio,
            followersCount = userDetails.followersCount,
            followingCount = userDetails.followingCount
        )
        usersDao.insertUserDetails(userDetailsEntity)
        usersDao.insertUsers(listOf(userEntity))
    }
}

private fun User.asEntity() = UserEntity(
    userId = this.id.data,
    username = this.username,
    email = this.email,
    avatarUrl = this.avatarUrl
)

private fun UserEntity.asUser() = User(
    id = UserId(this.userId),
    username = this.username,
    email = this.email,
    avatarUrl = this.avatarUrl
)

private fun UserAndDetails.convert(): UserDetails {
    this.details ?: throw IllegalArgumentException("We shouldn't be here")
    return UserDetails(
        id = UserId(this.user.userId),
        username = this.user.username,
        avatarUrl = this.user.avatarUrl,
        company = this.details.company.orEmpty(),
        location = this.details.location.orEmpty(),
        bio = this.details.bio.orEmpty(),
        email = this.user.email,
        followersCount = this.details.followersCount,
        followingCount = this.details.followingCount
    )
}