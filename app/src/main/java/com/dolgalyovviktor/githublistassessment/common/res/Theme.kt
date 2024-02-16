package com.dolgalyovviktor.githublistassessment.common.res

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.dolgalyovviktor.githublistassessment.R

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = colorResource(id = R.color.purple_200),
            secondary = colorResource(id = R.color.teal_200)
        )
    } else {
        lightColorScheme(
            primary = colorResource(id = R.color.purple_700),
            secondary = colorResource(id = R.color.teal_200)
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}