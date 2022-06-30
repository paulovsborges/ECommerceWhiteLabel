package com.pvsb.core.firestore.model

import com.google.firebase.firestore.PropertyName

data class CreateCartDTO(
    val id: String = ""
)

data class PopulateCartDTO(
    val products: List<CartProductsDTO>
)

data class CartProductsDTO(
    @get:PropertyName("product_id")
    @set:PropertyName("product_id")
    var product: ProductDTO,
    var amount: Int
)