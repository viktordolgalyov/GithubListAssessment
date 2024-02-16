package com.dolgalyovviktor.githublistassessment.data.local.user.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false) val userId: Int,
    val username: String,
    val email: String,
    val avatarUrl: String
)