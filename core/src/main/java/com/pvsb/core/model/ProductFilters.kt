package com.pvsb.core.model

data class ProductFilters(
    val price: ProductFiltersPrice,
    val name: String,
    var isChecked: Boolean = false
)

data class ProductFiltersPrice(
    var minValue: Double,
    var maxValue: Double
)