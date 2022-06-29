package com.pvsb.core.utils

sealed class ResponseState {
    object Init : ResponseState()
    object Loading : ResponseState()
    sealed class Complete : ResponseState() {
        object Empty : Complete()
        class Success<T>(val data: T) : Complete()
        class Fail(val exception: Throwable) : Complete()
    }
}