package com.pvsb.core.firestore.model

data class CreateCartDTO(
    val id: Int = 0,
    val totalPrice: Double = 0.0,
    val isActive: Boolean = false,
    val products: List<ProductDTO> = emptyList()
)