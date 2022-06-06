package com.pvsb.ecommercewhitelabel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DiffUtil

fun FragmentActivity.switchFragment(fragment: Fragment, container: Int, tag: String = "", animation: Boolean = false, stackName: String? = null, addToStack: Boolean = true) {
    supportFragmentManager.beginTransaction().apply {
        replace(container, fragment, tag)
        if (animation) setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (addToStack) {
            supportFragmentManager.findFragmentById(container)?.let {
                addToBackStack(stackName)
            }
        }
        setReorderingAllowed(true)
        commit()
    }
}

class ListAdapterDiffUtil<T :Any> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem.toString() == newItem.toString()

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
}