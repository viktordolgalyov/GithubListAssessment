package com.dolgalyovviktor.githublistassessment.app.navigation

interface Navigator {
    fun forward(path: String)
    fun back()

    sealed class Command {
        data class Forward(val path: String) : Command()
        data object Back : Command()
    }
}