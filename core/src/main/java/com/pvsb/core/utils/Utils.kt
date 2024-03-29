package com.pvsb.core.utils

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DiffUtil
import java.text.SimpleDateFormat
import java.util.Locale

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
    return "$$formatted"
}

fun String.formatLength(length: Int = 10): String {

    val formattedString = if (this.length > length) {
        "${this.take(length + 3)} ..."
    } else {
        this
    }

    return formattedString
}

class ListAdapterDiffUtil<T : Any> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem.toString() == newItem.toString()

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
}

fun String.formatDate(): String {
    val locale = Locale.getDefault()
    val fromFormat = "EEE MMM dd HH:mm:ss z yyyy"
    val toFormat = "dd/MM/yyyy"

    val result: String = kotlin.run {

        val getDateFromString = SimpleDateFormat(fromFormat, Locale.ENGLISH).parse(this)
        getDateFromString?.let {
            SimpleDateFormat(toFormat, locale).format(getDateFromString)
        } ?: ""
    }

    return result
}

fun String.getUserFirstName(): String {
    var firstName = ""
    val whiteSpaceIndex = this.indexOf(" ")
    var index = 0
    for (c in this.toCharArray()) {
        if (index < whiteSpaceIndex) {
            firstName += c.toString()
        } else {
            break
        }
        index += 1
    }

    return firstName
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}
fun View.invisible(){
    this.visibility = View.INVISIBLE
}