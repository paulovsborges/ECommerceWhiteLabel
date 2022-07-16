package com.pvsb.core.utils

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

fun Fragment.popBackStack() {
    activity?.onBackPressed()
}

fun Fragment.clearBackStack(stackName: String) {
    parentFragmentManager.clearBackStack(stackName)
}

fun Fragment.switchFragment(
    fragment: Fragment,
    data: Bundle? = null,
    tag: String = "",
    animation: Boolean = false,
    clearBackStack: Boolean? = false
) {

    parentFragmentManager.let { fm ->
        fm.beginTransaction().apply {

            val parentContainerId = (view?.parent as? ViewGroup)?.id

            parentContainerId?.let { container ->
                replace(container, fragment, tag)
                if (animation) setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                val stackName = parentFragmentManager.toString()
                addToBackStack(stackName)

                if (clearBackStack == true) {
                    remove(this@switchFragment)
                    clearBackStack(stackName)
                }

                data?.let {
                    fragment.arguments = it
                }

                setReorderingAllowed(true)
                commit()
            }
        }
    }
}

fun Fragment.closeActivityAndNavigate(
    activity: AppCompatActivity,
    action: String
) {

    this.activity?.apply {
        val intent = Intent(this, activity::class.java).apply {
            this.action = action
        }

        startActivity(intent)
        finish()
    }
}

fun Fragment.showLoading() {
    activity?.showLoading()
}

fun Fragment.hideLoading() {
    activity?.hideLoading()
}

suspend fun <T> Fragment.handleResponse(
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
