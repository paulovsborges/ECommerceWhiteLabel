package com.pvsb.ecommercewhitelabel.data.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDTO(
    @get:PropertyName("name")
    @set:PropertyName("name")
    var title: String = "",
    val price: String = ""
): Parcelable