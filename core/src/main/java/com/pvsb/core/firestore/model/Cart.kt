package com.pvsb.core.firestore.model

import com.google.firebase.firestore.PropertyName

data class PopulateCartDTO(
    val products: List<CartProductsDTO> = mutableListOf()
)

data class CartProductsDTO(
    @get:PropertyName("product_id")
    @set:PropertyName("product_id")
    var product: ProductDTO = ProductDTO(),
    var amount: Int = 0
)