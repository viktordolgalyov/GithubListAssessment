package com.dolgalyovviktor.githublistassessment.app.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dolgalyovviktor.githublistassessment.ui.user_details.UserDetailsScreen
import com.dolgalyovviktor.githublistassessment.ui.user_list.UserListScreen

private const val TRANSITION_LENGTH_MS = 300
fun NavGraphBuilder.registerRoute(route: Route) = when (route) {
    Route.UserList -> composable(route.path) { UserListScreen() }

    Route.UserDetails -> {
        val args = listOf(NavigationArg.Username.create())
        composable(
            route = route.path, arguments = args,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() }
        ) { backStackEntry ->
            val username = NavigationArg.Username.parse(backStackEntry).orEmpty()
            UserDetailsScreen(username = username)
        }
    }
}

private fun enterTransition(): EnterTransition {
    return fadeIn(tween(TRANSITION_LENGTH_MS, easing = LinearEasing)) +
            slideInHorizontally(tween(TRANSITION_LENGTH_MS, easing = EaseIn))
}

private fun exitTransition(): ExitTransition {
    return fadeOut(tween(TRANSITION_LENGTH_MS, easing = LinearEasing)) +
            slideOutHorizontally(tween(TRANSITION_LENGTH_MS, easing = EaseOut))
}