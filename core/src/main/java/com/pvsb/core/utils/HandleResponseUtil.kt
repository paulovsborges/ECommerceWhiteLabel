package com.pvsb.core.utils

fun <T> ResponseState.handleResponseState(
    onLoading: (Boolean) -> Unit,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit,
    onEmpty: (() -> Unit)? = null
) {

    var showLoading = true

    when (this) {
        is ResponseState.Init -> {}
        is ResponseState.Loading -> {
            onLoading.invoke(showLoading)
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
                    val result = this.data as? T
                    result?.let {
                        onSuccess.invoke(result)
                    }
                }
            }
            showLoading = false
            onLoading.invoke(showLoading)
        }
    }
}