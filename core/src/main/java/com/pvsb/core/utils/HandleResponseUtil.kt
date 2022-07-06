package com.pvsb.core.utils

import androidx.fragment.app.Fragment

fun <T> ResponseState.handleResponseState(
    fragment: Fragment,
    autoLoading: Boolean = true,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit,
    onEmpty: (() -> Unit)? = null
) {
    when (this) {
        is ResponseState.Init -> {}
        is ResponseState.Loading -> {
            if (autoLoading) fragment.showLoading()
        }
        is ResponseState.Complete -> {
            when (this) {
                is ResponseState.Complete.Empty -> {
                    onEmpty?.invoke()
                }
                is ResponseState.Complete.Fail -> {
                    onError.invoke(exception)
                }
                is ResponseState.Complete.Success<*> -> {
                    (data as? ResponseState.Complete.Success<T>)?.also { response ->
                        onSuccess.invoke(response.data)
                    }
                }
            }
            if (autoLoading) fragment.hideLoading()
        }
    }
}