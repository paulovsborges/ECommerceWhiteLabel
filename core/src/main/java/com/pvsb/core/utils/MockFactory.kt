package com.pvsb.core.utils

import com.pvsb.core.model.ProductFilterCategories

sealed class MockFactory {

    object Filters {
        val categoriesList = listOf(
            ProductFilterCategories(
                id = 1,
                name = "Hardware"
            ),
            ProductFilterCategories(
                id = 2,
                name = "Peripherals"
            ),
            ProductFilterCategories(
                id = 3,
                name = "Cases"
            )
        )
    }
}