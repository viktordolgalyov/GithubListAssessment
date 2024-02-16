package com.dolgalyovviktor.githublistassessment.app.di

import android.content.Context
import androidx.room.Room
import com.dolgalyovviktor.githublistassessment.app.navigation.AppNavigator
import com.dolgalyovviktor.githublistassessment.app.navigation.Navigator
import com.dolgalyovviktor.githublistassessment.common.arch.AppDispatchers
import com.dolgalyovviktor.githublistassessment.data.local.AppDatabase
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module
import timber.log.Timber
import java.util.concurrent.TimeUnit

private const val APP_DATABASE_NAME = "users-db"

val appModule = module {
    single { createKtorClient() }
    single { createRoom(androidContext()) }
    single { get<AppDatabase>().userDao() }
    single {
        AppDispatchers(
            subscribe = Dispatchers.IO,
            observe = Dispatchers.Main
        )
    }
    single { AppNavigator() } bind Navigator::class
}

private fun createKtorClient() = HttpClient(OkHttp) {
    engine {
        config {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
        }
    }
    install(Logging) {
        level = LogLevel.BODY
        logger = object : Logger {
            override fun log(message: String) {
                Timber.i(message)
            }
        }
    }
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            explicitNulls = false
            coerceInputValues = true
        })
    }
}

private fun createRoom(appContext: Context) = Room
    .databaseBuilder(appContext, AppDatabase::class.java, APP_DATABASE_NAME)
    .fallbackToDestructiveMigration()
    .build()