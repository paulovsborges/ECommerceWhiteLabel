package com.pvsb.core.utils

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.KClass

inline fun <reified T : Any> Fragment.setUpActivityListener(
    extraKey: String,
    crossinline resultOk: (T) -> Unit,
): ActivityResultLauncher<Intent> =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        if (it.resultCode == Activity.RESULT_OK) {

            if (!isPrimitiveType(T::class)) {

                val data = it.data?.extras?.get(extraKey) as String
                val value = Json.decodeFromString<T>(data)
                resultOk.invoke(value)
            } else {

                @Suppress("UNCHECKED_CAST")
                val data = it.data?.extras?.get(extraKey) as? T

                data?.let {
                    resultOk.invoke(data)
                }
            }
        }
    }

inline fun <reified T> FragmentActivity.setResultAndFinish(data: T) {

    val tag = this.javaClass.simpleName

    val intent = Intent().apply {
        when (T::class) {
            String::class -> {
                putExtra(tag, data as String)
            }
            Int::class -> {
                putExtra(tag, data as Int)
            }
            Boolean::class -> {
                putExtra(tag, data as Boolean)
            }
            Double::class -> {
                putExtra(tag, data as Double)
            }
            Float::class -> {
                putExtra(tag, data as Float)
            }
            else -> {
                val customObject = Json.encodeToString(data)
                putExtra(tag, customObject)
            }
        }
    }

    setResult(Activity.RESULT_OK, intent)
    finish()
}

inline fun isPrimitiveType(obj: KClass<*>): Boolean {

    val types = mutableSetOf<KClass<*>>()
    types.add(String::class)
    types.add(Boolean::class)
    types.add(Int::class)
    types.add(Float::class)
    types.add(Double::class)

    return types.contains(obj)
}

@Serializable
data class Testing(
    val count: Int,
    val from: String
)