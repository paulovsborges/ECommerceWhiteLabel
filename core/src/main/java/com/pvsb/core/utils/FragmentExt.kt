package com.pvsb.core.utils

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
    animation: Boolean = false
) {

    activity?.supportFragmentManager?.let { fm ->
        fm.beginTransaction().apply {

            val parentContainerId = (view?.parent as? ViewGroup)?.id

            parentContainerId?.let { container ->
                replace(container, fragment, tag)
                if (animation) setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                val stackName = parentFragmentManager.toString()
                addToBackStack(stackName)

                data?.let {
                    fragment.arguments = it
                }

                setReorderingAllowed(true)
                commit()
            }
        }
    }
}