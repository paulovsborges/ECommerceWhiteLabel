package com.pvsb.ecommercewhitelabel.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainHomeModel(
    val title: String,
    val price: String
): Parcelable