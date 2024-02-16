package com.dolgalyovviktor.githublistassessment.app.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AppNavigator : Navigator {
    private val _commands = MutableSharedFlow<Navigator.Command>(
        replay = 0,
        extraBufferCapacity = Int.MAX_VALUE
    )
    val commands: SharedFlow<Navigator.Command>
        get() = _commands.asSharedFlow()

    override fun forward(path: String) {
        _commands.tryEmit(Navigator.Command.Forward(path))
    }

    override fun back() {
        _commands.tryEmit(Navigator.Command.Back)
    }
}