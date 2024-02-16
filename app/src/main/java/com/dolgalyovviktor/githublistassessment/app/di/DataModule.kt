package com.dolgalyovviktor.githublistassessment.app.di

import com.dolgalyovviktor.githublistassessment.data.GithubUsersRepository
import com.dolgalyovviktor.githublistassessment.data.UsersRepository
import com.dolgalyovviktor.githublistassessment.data.local.user.UsersLocalSource
import com.dolgalyovviktor.githublistassessment.data.remote.GithubUsersRemoteSource
import org.koin.dsl.module

val dataModule = module {
    single { UsersLocalSource(get()) }
    single { GithubUsersRemoteSource(get()) }
    single<UsersRepository> { GithubUsersRepository(get(), get()) }
}