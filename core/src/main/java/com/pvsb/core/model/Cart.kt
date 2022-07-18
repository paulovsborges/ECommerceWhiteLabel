package com.pvsb.core.model

data class PopulateCartDTO(
    val products: List<CartProductsDTO> = mutableListOf(),
    val total: Double = 0.0
)

data class CartProductsDTO(
    var product: ProductDTO = ProductDTO(),
    var amount: Int = 0
)