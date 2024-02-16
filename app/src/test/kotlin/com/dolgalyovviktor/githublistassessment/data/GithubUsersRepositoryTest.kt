package com.dolgalyovviktor.githublistassessment.data

import com.dolgalyovviktor.githublistassessment.data.local.user.UsersLocalSource
import com.dolgalyovviktor.githublistassessment.data.remote.GithubUsersRemoteSource
import com.dolgalyovviktor.githublistassessment.domain.model.User
import com.dolgalyovviktor.githublistassessment.domain.model.UserDetails
import com.dolgalyovviktor.githublistassessment.domain.model.UserListRequest
import com.dolgalyovviktor.githublistassessment.domain.model.UserListResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GithubUsersRepositoryTest {
    private val localSource: UsersLocalSource = mockk(relaxed = true)
    private val remoteSource: GithubUsersRemoteSource = mockk(relaxed = true)
    private val username = "username"
    private lateinit var repository: GithubUsersRepository

    @Before
    fun setUp() {
        repository = GithubUsersRepository(localSource, remoteSource)
    }

    @Test
    fun `getUser returns cached data if available`() = runTest {
        val expectedUserDetails = mockk<UserDetails>()
        coEvery { expectedUserDetails.username } returns username

        coEvery { localSource.getUser(any()) } returns expectedUserDetails

        val result = repository.getUser(username)

        assertEquals(expectedUserDetails, result)
        coVerify(exactly = 0) { remoteSource.getUser(username) }
    }

    @Test
    fun `getUser fetches from remote and updates cache when no cache available`() = runTest {
        val remoteUserDetails = mockk<UserDetails>()
        coEvery { remoteUserDetails.username } returns username

        coEvery { localSource.getUser(any()) } returns null
        coEvery { remoteSource.getUser(any()) } returns remoteUserDetails

        val result = repository.getUser(username)

        assertEquals(remoteUserDetails, result)
        coVerify { localSource.storeUser(remoteUserDetails) }
    }

    @Test
    fun `getUsersList returns cached data if available`() = runTest {
        val request = mockk<UserListRequest>()
        val user = mockk<User>()
        val expectedResponse = UserListResponse(listOf(user))

        coEvery { localSource.getUserList(any()) } returns expectedResponse

        val result = repository.getUsersList(request)

        assertEquals(expectedResponse, result)
        coVerify(exactly = 0) { remoteSource.getUsers(request) }
    }

    @Test
    fun `getUsersList fetches from remote and updates cache when no cache available`() = runTest {
        val request = mockk<UserListRequest>()
        val remoteResponse = mockk<UserListResponse>()
        coEvery { localSource.getUserList(request) } returns null
        coEvery { remoteSource.getUsers(request) } returns remoteResponse

        val result = repository.getUsersList(request)

        assertEquals(remoteResponse, result)
        coVerify { localSource.storeUserListResponse(remoteResponse) }
    }
}