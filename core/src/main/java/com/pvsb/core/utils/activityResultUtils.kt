package com.pvsb.core.utils

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

fun <T : Any> Fragment.setUpActivityListener(
    extraKey: String,
    resultOk: (T) -> Unit
): ActivityResultLauncher<Intent> =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        @Suppress("UNCHECKED_CAST")
        val data = it.data?.extras?.get(extraKey) as T

        if (it.resultCode == Activity.RESULT_OK) {
            resultOk.invoke(data)
        }
    }
