package com.pvsb.core.utils

import android.content.Intent
import android.os.Bundle
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
    animation: Boolean = false
) {

    parentFragmentManager.let { fm ->
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