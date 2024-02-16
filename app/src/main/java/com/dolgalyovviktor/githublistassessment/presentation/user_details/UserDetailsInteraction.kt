package com.dolgalyovviktor.githublistassessment.presentation.user_details

import com.dolgalyovviktor.githublistassessment.common.arch.ExtraDataState
import com.dolgalyovviktor.githublistassessment.common.arch.UIAction
import com.dolgalyovviktor.githublistassessment.common.arch.UIStateChange
import com.dolgalyovviktor.githublistassessment.domain.model.UserDetails

sealed class UserDetailsAction : UIAction {
    data object BackClick : UserDetailsAction()
    data object RetryClick : UserDetailsAction()
}

sealed class UserDetailsChange : UIStateChange {
    data class UserDataLoaded(val data: UserDetails?) : UserDetailsChange()
    data class UserExtraDataStateChanged(val state: ExtraDataState) : UserDetailsChange()
}