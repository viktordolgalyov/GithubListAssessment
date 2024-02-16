package com.dolgalyovviktor.githublistassessment.domain

import com.dolgalyovviktor.githublistassessment.data.UsersRepository
import com.dolgalyovviktor.githublistassessment.domain.model.UserDetails
import com.dolgalyovviktor.githublistassessment.domain.model.UserId
import com.dolgalyovviktor.githublistassessment.domain.model.UserListResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UsersServiceTest {
    private val usersRepository = mockk<UsersRepository>()
    private lateinit var usersService: UsersService

    @Before
    fun setUp() {
        usersService = UsersService(usersRepository)
    }

    @Test
    fun `getUsers returns expected data`() = runTest {
        val mockUserId = UserId(1)
        val count = 1
        val expectedResponse = mockk<UserListResponse>()

        coEvery { usersRepository.getUsersList(request = any()) } returns expectedResponse

        val actualResponse = usersService.getUsers(since = mockUserId, count)

        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `getUser returns user for valid username`() = runTest {
        val username = "username"
        val expectedUser = mockk<UserDetails>()
        coEvery { expectedUser.username } returns username

        coEvery { usersRepository.getUser(any()) } returns expectedUser

        val actualUser = usersService.getUser(username)

        assertEquals(expectedUser, actualUser)
    }

    @Test(expected = Exception::class)
    fun `getUsersList throws an exception when repository fails`() = runTest {
        val mockUserId = UserId(1)

        coEvery { usersRepository.getUsersList(any()) } throws Exception("Network error")

        usersService.getUsers(mockUserId, 1)
    }
}