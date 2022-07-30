package com.pvsb.core.utils

sealed class ResponseState {
    object Start : ResponseState()
    object Loading : ResponseState()
    sealed class Complete : ResponseState() {
        object Empty : Complete()
        data class Success<T>(val data: T) : Complete()
        class Fail(val exception: Throwable) : Complete()
    }
}
