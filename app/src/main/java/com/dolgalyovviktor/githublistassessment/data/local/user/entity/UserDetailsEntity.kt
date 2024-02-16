package com.dolgalyovviktor.githublistassessment.data.local.user.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_details")
data class UserDetailsEntity(
    @PrimaryKey(autoGenerate = false) val userId: Int,
    val company: String?,
    val location: String?,
    val bio: String?,
    val followersCount: Int,
    val followingCount: Int
)