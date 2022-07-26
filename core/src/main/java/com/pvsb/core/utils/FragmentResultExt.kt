package com.pvsb.core.utils

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T : Any> Fragment.getValueFromFragmentListener(
    bundleKey: String,
    requestKey: String? = null,
    crossinline valueReceived: (T) -> Unit
) {

    val key = if (requestKey.isNullOrEmpty()) parentFragmentManager.toString() else requestKey

    setFragmentResultListener(key) { _, bundle ->
        when (T::class) {
            String::class -> {
                bundle.getString(bundleKey)?.let { valueReceived(it as T) }
            }
            Int::class -> {
                valueReceived(bundle.getInt(bundleKey) as T)
            }
            Boolean::class -> {
                valueReceived(bundle.getBoolean(bundleKey) as T)
            }
            Double::class -> {
                valueReceived(bundle.getDouble(bundleKey) as T)
            }
            Float::class -> {
                valueReceived(bundle.getFloat(bundleKey) as T)
            }
            else -> {
                bundle.getString(bundleKey)?.let { json ->
                    val decodedValue = Json.decodeFromString<T>(json)
                    valueReceived(decodedValue)
                }
            }
        }
    }
}

inline fun <reified T : Any> Fragment.setResultToFragmentListener(
    value: T,
    bundleKey: String,
    requestKey: String? = null
) {
    val key = if (requestKey.isNullOrEmpty()) parentFragmentManager.toString() else requestKey
    if (!isPrimitiveType(T::class)) {
        val encodedValue = Json.encodeToString(value)
        setFragmentResult(key, bundleOf(bundleKey to encodedValue))
    } else {
        setFragmentResult(key, bundleOf(bundleKey to value))
    }
}