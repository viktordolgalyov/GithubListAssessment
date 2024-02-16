package com.dolgalyovviktor.githublistassessment.common.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class FluxViewModel<A : UIAction, C : UIStateChange, S : UIState, M : UIModel>(
    initialState: S,
    protected val dispatchers: AppDispatchers,
    private val reducer: Reducer<S, C>,
    private val stateToModelMapper: StateToModelMapper<S, M>
) : ViewModel() {
    private val modelName = javaClass.simpleName

    private val changes = MutableSharedFlow<C>(replay = 0, extraBufferCapacity = Int.MAX_VALUE)
    private val actions = MutableSharedFlow<A>(replay = 0, extraBufferCapacity = Int.MAX_VALUE)
    protected open val errorHandler: ErrorHandler by lazy { { Timber.e(it) } }
    protected var state: S
        private set

    private val _model = MutableStateFlow<M?>(null)
    val model: StateFlow<M?> = _model.asStateFlow()

    init {
        this.state = initialState
        Timber.d("${modelName}: Created")
        collectActions()
        bindChanges()
    }

    fun dispatch(action: A) {
        viewModelScope.launch(dispatchers.subscribe) { actions.emit(action) }
    }

    fun sendChange(change: C) {
        viewModelScope.launch(dispatchers.subscribe) { changes.emit(change) }
    }

    protected abstract fun processAction(action: A): Any?

    private fun collectActions() {
        viewModelScope.launch(dispatchers.subscribe) {
            actions.collect { action ->
                if (action.isLoggable) {
                    Timber.d("${modelName}: Received action: $action")
                }
                runCatching { processAction(action) }.onFailure(::onError)
            }
        }
    }

    private fun bindChanges() {
        viewModelScope.launch(dispatchers.subscribe) {
            changes
                .distinctUntilChanged()
                .map { change ->
                    if (change.isLoggable) {
                        Timber.d("${modelName}: Received change: $change")
                    }
                    reducer.reduce(state, change).also { this@FluxViewModel.state = it }
                }
                .distinctUntilChanged()
                .onStart { emit(state) }
                .map { stateToModelMapper.mapStateToModel(it) }
                .distinctUntilChanged()
                .catch { e -> onError(e) }
                .collectLatest { model ->
                    _model.emit(model)
                    if (model.isLoggable) {
                        Timber.d("${modelName}: Model updated: $model")
                    }
                }
        }
    }

    override fun onCleared() {
        Timber.d("${modelName}: Destroyed")
    }

    protected fun onError(error: Throwable) {
        Timber.e(error)
        viewModelScope.launch(dispatchers.observe) { errorHandler(error) }
    }
}