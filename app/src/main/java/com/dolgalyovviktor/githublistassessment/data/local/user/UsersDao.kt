package com.dolgalyovviktor.githublistassessment.data.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.dolgalyovviktor.githublistassessment.data.local.user.entity.UserAndDetails
import com.dolgalyovviktor.githublistassessment.data.local.user.entity.UserDetailsEntity
import com.dolgalyovviktor.githublistassessment.data.local.user.entity.UserEntity

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserDetails(details: UserDetailsEntity)

    @Query(
        "SELECT * FROM users " +
                "WHERE userId > :sinceId " +
                "ORDER BY userId ASC " +
                "LIMIT :itemCount"
    )
    suspend fun getUsers(sinceId: Int, itemCount: Int): List<UserEntity>

    @Query(
        "SELECT u.*, ud.* " +
                "FROM users AS u " +
                "LEFT JOIN user_details AS ud ON u.userId = ud.userId " +
                "WHERE u.username = :username"
    )
    @Transaction
    suspend fun getUserByUsername(username: String): UserAndDetails?
}