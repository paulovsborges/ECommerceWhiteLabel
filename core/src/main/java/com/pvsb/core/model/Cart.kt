package com.pvsb.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class PopulateCartDTO(
    val products: List<CartProductsDTO> = mutableListOf(),
    val total: Double = 0.0
)

@Parcelize
data class CartProductsDTO(
    var product: ProductDTO = ProductDTO(),
    var amount: Int = 0
): Parcelable