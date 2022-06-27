package com.pvsb.core.firestore.model

data class ProductFilters(
    val name: String,
    var isChecked: Boolean = false
)