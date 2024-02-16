package com.dolgalyovviktor.githublistassessment.app

import android.app.Application
import com.dolgalyovviktor.githublistassessment.app.di.appModule
import com.dolgalyovviktor.githublistassessment.app.di.dataModule
import com.dolgalyovviktor.githublistassessment.app.di.domainModule
import com.dolgalyovviktor.githublistassessment.app.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import timber.log.Timber.DebugTree

class GithubListApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@GithubListApp)
            modules(
                listOf(
                    appModule,
                    dataModule,
                    domainModule,
                    presentationModule
                )
            )
        }
    }
}