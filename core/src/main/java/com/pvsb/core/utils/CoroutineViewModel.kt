package com.pvsb.core.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlin.coroutines.CoroutineContext

abstract class CoroutineViewModel : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}

fun ViewModel.buildStateFlow(sourceCall: Flow<ResponseState>): StateFlow<ResponseState> =
    sourceCall
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(replayExpirationMillis = 0),
            ResponseState.Init
        )