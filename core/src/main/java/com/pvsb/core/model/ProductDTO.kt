package com.pvsb.core.model

import com.google.firebase.firestore.PropertyName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDTO(
    val id: Int = 0,
    val title: String = "",
    val price: Double = 0.0,
    @get:PropertyName("image_url")
    @set:PropertyName("image_url")
    var imageUrl: String = "",
    @get:PropertyName("category_id")
    @set:PropertyName("category_id")
    var categoryId: Int = 0
)
