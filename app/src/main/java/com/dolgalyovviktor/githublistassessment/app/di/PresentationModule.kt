package com.dolgalyovviktor.githublistassessment.app.di

import com.dolgalyovviktor.githublistassessment.presentation.user_details.UserDetailsReducer
import com.dolgalyovviktor.githublistassessment.presentation.user_details.UserDetailsStateModelMapper
import com.dolgalyovviktor.githublistassessment.presentation.user_details.UserDetailsViewModel
import com.dolgalyovviktor.githublistassessment.presentation.user_list.UserListReducer
import com.dolgalyovviktor.githublistassessment.presentation.user_list.UserListStateModelMapper
import com.dolgalyovviktor.githublistassessment.presentation.user_list.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        UserListViewModel(
            usersService = get(),
            navigator = get(),
            reducer = UserListReducer(),
            mapper = UserListStateModelMapper(),
            dispatchers = get()
        )
    }
    viewModel { (username: String) ->
        UserDetailsViewModel(
            usersService = get(),
            navigator = get(),
            username = username,
            reducer = UserDetailsReducer(),
            mapper = UserDetailsStateModelMapper(),
            dispatchers = get()
        )
    }
}