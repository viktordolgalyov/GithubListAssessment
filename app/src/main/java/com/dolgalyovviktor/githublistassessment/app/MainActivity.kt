package com.dolgalyovviktor.githublistassessment.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dolgalyovviktor.githublistassessment.app.navigation.AppNavigator
import com.dolgalyovviktor.githublistassessment.app.navigation.Navigator
import com.dolgalyovviktor.githublistassessment.app.navigation.Route
import com.dolgalyovviktor.githublistassessment.app.navigation.registerRoute
import com.dolgalyovviktor.githublistassessment.common.res.AppTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    content = { App() }
                )
            }
        }
    }

    @Composable
    private fun App() {
        val navController = rememberNavController()
        val appNavigator by inject<AppNavigator>()
        NavHost(navController = navController, startDestination = Route.UserList.path) {
            Route.all().forEach { registerRoute(it) }
        }
        LaunchedEffect("navigation") {
            appNavigator.commands.collect {
                when (it) {
                    Navigator.Command.Back -> navController.popBackStack()
                    is Navigator.Command.Forward -> navController.navigate(it.path)
                }
            }
        }
    }
}