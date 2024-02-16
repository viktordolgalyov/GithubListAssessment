package com.dolgalyovviktor.githublistassessment.app.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavigationArg<T>(val name: String) {
    abstract fun create(): NamedNavArgument
    abstract fun parse(backStackEntry: NavBackStackEntry): T?

    data object Username : NavigationArg<String>(name = "username") {

        override fun create() = navArgument(name) { type = NavType.StringType }

        override fun parse(
            backStackEntry: NavBackStackEntry
        ): String? = backStackEntry.arguments?.getString(name)
    }
}