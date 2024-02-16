package com.dolgalyovviktor.githublistassessment.data.local.user.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserAndDetails(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val details: UserDetailsEntity?
)