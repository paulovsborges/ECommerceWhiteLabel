package com.pvsb.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductFilters(
    val price: ProductFiltersPrice,
    val categories: List<ProductFilterCategories>,
)

@Serializable
data class ProductFilterCategories(
    val id: Int,
    val name: String,
    var isChecked: Boolean = false
)

@Serializable
data class ProductFiltersPrice(
    var minValue: Double,
    var maxValue: Double
)