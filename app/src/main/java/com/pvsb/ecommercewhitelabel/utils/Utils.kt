package com.pvsb.ecommercewhitelabel.utils

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DiffUtil
import com.pvsb.ecommercewhitelabel.R

fun Fragment.switchFragment(
    fragment: Fragment,
    container: Int,
    data: Bundle? = null,
    tag: String = "",
    animation: Boolean = false,
    stackName: String? = null,
    addToStack: Boolean = false
) {

    childFragmentManager.beginTransaction().apply {
        replace(container, fragment, tag)
        if (animation) setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (addToStack) {
            childFragmentManager.findFragmentById(container)?.let {
                addToBackStack(stackName)
            }
        }

        data?.let {
            fragment.arguments = it
        }

        setReorderingAllowed(true)
        commit()
    }
}

fun Fragment.openFragmentFullScreen(
    fragment: Fragment,
    data: Bundle? = null,
    tag: String = "",
    animation: Boolean = false,
    stackName: String? = null,
    addToStack: Boolean = false
) {

    val container = R.id.mainContainer

    requireActivity().supportFragmentManager.beginTransaction().apply {
        replace(container, fragment, tag)
        if (animation) setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (addToStack) {
            childFragmentManager.findFragmentById(container)?.let {
                addToBackStack(stackName)
            }
        }

        data?.let {
            fragment.arguments = it
        }

        setReorderingAllowed(true)
        commit()
    }
}

class ListAdapterDiffUtil<T : Any> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem.toString() == newItem.toString()

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
}