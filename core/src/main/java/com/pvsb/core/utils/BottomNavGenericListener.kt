package com.pvsb.core.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.navigation.NavigationBarView

fun FragmentActivity.createBottomNavListener(
    container: Int,
    map: Map<Int, Fragment>
) = NavigationBarView.OnItemSelectedListener { item ->
    map.forEach { (id, fragment) ->
        if (item.itemId == id) {
            switchFragment(fragment, container)
            return@OnItemSelectedListener true
        }
    }
    return@OnItemSelectedListener false
}