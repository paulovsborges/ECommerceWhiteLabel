package com.pvsb.core.utils

import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentActivity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


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

suspend fun <T> FragmentActivity.handleResponse(
    state: ResponseState,
    onSuccess: suspend (T) -> Unit,
    onError: suspend (Throwable) -> Unit,
    onEmpty: (suspend () -> Unit)? = null
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

inline fun <reified T : Any> Intent.putValueOnBundle(key: String, item: T) {
    when (T::class) {
        String::class -> {
            putExtra(key, item as String)
        }
        Int::class -> {
            putExtra(key, item as Int)
        }
        Boolean::class -> {
            putExtra(key, item as Boolean)
        }
        Double::class -> {
            putExtra(key, item as Double)
        }
        Float::class -> {
            putExtra(key, item as Float)
        }
        else -> {
            val encodedValue = Json.encodeToString(item)
            putExtra(key, encodedValue)
        }
    }
}

inline fun <reified T : Any> FragmentActivity.getValueFromBundle(key: String): T? {

    var value: T? = null

    when (T::class) {
        String::class -> {
            value = intent.getStringExtra(key) as T
        }
        Int::class -> {
            value = intent.getIntExtra(key, 0) as T
        }
        Boolean::class -> {
            value = intent.getBooleanExtra(key, false) as T
        }
        Double::class -> {
            value = intent.getDoubleExtra(key, 0.0) as T
        }
        Float::class -> {
            value = intent.getFloatExtra(key, 0f) as T
        }
        else -> {
            val valueFromIntent = intent.getStringExtra(key)

            valueFromIntent?.let {
                val decodedValue = Json.decodeFromString<T>(valueFromIntent)
                value = decodedValue
            }
        }
    }

    return value
}