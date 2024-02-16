package com.dolgalyovviktor.githublistassessment.common.arch

interface UIAction : FluxLoggable

interface UIStateChange : FluxLoggable

interface UIState : FluxLoggable

interface UIModel : FluxLoggable

interface Reducer<S : UIState, C : UIStateChange> {
    fun reduce(state: S, change: C): S
}

interface StateToModelMapper<S : UIState, M : UIModel> {
    fun mapStateToModel(state: S): M
}

interface FluxLoggable {
    val isLoggable: Boolean
        get() = true

    fun log(): String = toString()
}