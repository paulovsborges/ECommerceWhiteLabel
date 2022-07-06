package com.pvsb.core.utils

import android.util.Log
import androidx.fragment.app.FragmentActivity


fun FragmentActivity.showLoading() {
    if (supportFragmentManager.findFragmentByTag("loading") == null) {
        FragmentLoading().show(supportFragmentManager, "loading")
        try {
            supportFragmentManager.executePendingTransactions()
        } catch (e: Exception) {
            Log.e("Fragment manager", e.toString())
        }
    }
}

fun FragmentActivity.hideLoading() {
    (supportFragmentManager.findFragmentByTag("loading") as? FragmentLoading)?.also { loading ->
        loading.dismiss()
    }
}


fun <T> FragmentActivity.handleResponse(
    state: ResponseState,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit,
    onEmpty: (() -> Unit)? = null
) {

    state.handleResponseState<T>(
        onLoading = {
            if (it) showLoading() else hideLoading()
        },
        onSuccess = {
            onSuccess.invoke(it)
        },
        onError = {
            onError.invoke(it)
        },
        onEmpty = {
            onEmpty?.invoke()
        }
    )
}