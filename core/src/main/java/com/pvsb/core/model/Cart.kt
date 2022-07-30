package com.pvsb.core.model

import kotlinx.serialization.Serializable

data class PopulateCartDTO(
    val products: List<CartProductsDTO> = mutableListOf(),
    val total: Double = 0.0
)

@Serializable
data class CartProductsDTO(
    var product: ProductDTO = ProductDTO(),
    var amount: Int = 0
)
