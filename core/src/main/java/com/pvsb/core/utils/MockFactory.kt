package com.pvsb.core.utils

import com.pvsb.core.firebase.model.ProductFilters

sealed class MockFactory {

    object Filters {
        val list = listOf(
            ProductFilters("airfryer"),
            ProductFilters("geladeira"),
            ProductFilters("galaxy"),
            ProductFilters("teclado"),
        )
    }
}