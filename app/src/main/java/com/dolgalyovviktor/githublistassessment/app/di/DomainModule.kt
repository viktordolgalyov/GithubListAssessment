package com.dolgalyovviktor.githublistassessment.app.di

import com.dolgalyovviktor.githublistassessment.domain.UsersService
import org.koin.dsl.module

val domainModule = module {
    single { UsersService(get()) }
}