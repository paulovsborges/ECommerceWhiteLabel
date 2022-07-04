package com.pvsb.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DiffUtil
import java.util.*

fun FragmentActivity.switchFragment(
    fragment: Fragment,
    container: Int,
    data: Bundle? = null,
    tag: String = "",
    animation: Boolean = false,
    stackName: String? = null
) {

    supportFragmentManager.beginTransaction().apply {
        replace(container, fragment, tag)
        if (animation) setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

        stackName?.let {
            supportFragmentManager.findFragmentById(container)?.let {
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

fun Context.openActivity(activity: Class<*>, data: ((Intent) -> Unit)? = null) {
    val intent = Intent(this, activity)
    data?.invoke(intent)
    startActivity(intent)
}

fun Double.formatCurrency(): String {
    val formatted = String.format("%.2f", this)
    return "R$${formatted}"
}

class ListAdapterDiffUtil<T : Any> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem.toString() == newItem.toString()

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
}