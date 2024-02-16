package com.dolgalyovviktor.githublistassessment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dolgalyovviktor.githublistassessment.data.local.user.UsersDao
import com.dolgalyovviktor.githublistassessment.data.local.user.entity.UserDetailsEntity
import com.dolgalyovviktor.githublistassessment.data.local.user.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        UserDetailsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UsersDao
}