package com.pvsb.core.utils

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Fragment.onBackPress() {
    activity?.onBackPressed()
}

fun Fragment.clearBackStack() {
    parentFragmentManager.clearBackStack(parentFragmentManager.toString())
}

fun Fragment.popBackStack() {
    parentFragmentManager.popBackStack()
}

fun Fragment.switchFragment(
    fragment: Fragment,
    animation: Boolean = false,
    saveBackStack: Boolean = false
) {

    parentFragmentManager.let { fm ->
        fm.beginTransaction().apply {

            val parentContainerId = (view?.parent as? ViewGroup)?.id

            parentContainerId?.let { container ->
                replace(container, fragment, parentFragmentManager.toString())
                if (animation) setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                if (saveBackStack) {
                    val stackName = parentFragmentManager.toString()
                    addToBackStack(stackName)
                }

                setReorderingAllowed(true)
                commit()
            }
        }
    }
}

inline fun <reified T : Any> Fragment.switchFragmentWithArgs(
    fragment: Fragment,
    data: T? = null,
    animation: Boolean = true,
    saveBackStack: Boolean = false
) {

    parentFragmentManager.let { fm ->
        fm.beginTransaction().apply {

            val parentContainerId = (view?.parent as? ViewGroup)?.id

            parentContainerId?.let { container ->
                replace(container, fragment, parentFragmentManager.toString())
                if (animation) setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                if (saveBackStack) {
                    val stackName = parentFragmentManager.toString()
                    addToBackStack(stackName)
                }

                data?.let {
                    fragment.arguments = serializeObjToArguments(data, fragment)
                }

                setReorderingAllowed(true)
                commit()
            }
        }
    }
}

inline fun <reified T : Any> serializeObjToArguments(
    data: T,
    fragment: Fragment
): Bundle {

    val value: Any?

    value = if (!isPrimitiveType(T::class)) {
        Json.encodeToString(data)
    } else {
        data
    }

    return bundleOf(fragment.javaClass.simpleName to value)
}

inline fun <reified T : Any> Fragment.deserializeObjFromArguments(): T? {

    var value: T? = null

    if (isPrimitiveType(T::class)) {
        value = arguments?.get(javaClass.simpleName) as T?
    } else {
        val valueFromArgs = arguments?.get(javaClass.simpleName) as? String
        valueFromArgs?.let {
            value = Json.decodeFromString(valueFromArgs)
        }
    }

    return value
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
