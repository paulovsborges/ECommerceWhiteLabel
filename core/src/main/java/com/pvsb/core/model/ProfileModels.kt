package com.pvsb.core.model

import com.google.firebase.firestore.PropertyName
import kotlinx.serialization.Serializable

@Serializable
data class UserPersonalData(
    @get:PropertyName("user_id")
    @set:PropertyName("user_id")
    var userId: String = "",
    val name: String = "",
    val birth: String = ""
)

data class UserFavoritesResDTO(
    var products: List<ProductDTO> = emptyList()
)

data class UserAddressesResDTO(
    var addresses: List<UserAddressDTO> = emptyList()
)