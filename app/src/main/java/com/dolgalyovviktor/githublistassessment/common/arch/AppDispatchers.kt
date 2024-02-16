package com.dolgalyovviktor.githublistassessment.common.arch

import kotlinx.coroutines.CoroutineDispatcher

data class AppDispatchers(
    val subscribe: CoroutineDispatcher,
    val observe: CoroutineDispatcher
)