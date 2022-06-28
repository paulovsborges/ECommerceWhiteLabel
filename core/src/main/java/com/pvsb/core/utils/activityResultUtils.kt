package com.pvsb.core.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun <T : Any> Fragment.setUpActivityListener(
    extraKey: String,
    resultOk: (T) -> Unit
): ActivityResultLauncher<Intent> =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        @Suppress("UNCHECKED_CAST")
        val data = it.data?.extras?.get(extraKey) as? T

        if (it.resultCode == Activity.RESULT_OK) {
            data?.let {
                resultOk.invoke(data)
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