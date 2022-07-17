package com.pvsb.core.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserAddressDTO(
    @get:PropertyName("zip_code")
    @set:PropertyName("zip_code")
    var zipCode: String = "",
    var street: String = "",
    var neighbour: String = "",
    var city: String = "",
    var state: String = "",
    var complement: String = "",
    var number: String = "",
    var addressNick: String = ""
) : Parcelable