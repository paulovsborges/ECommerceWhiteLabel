package com.pvsb.core.utils

import com.pvsb.core.model.ProductDTO
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

    object Products {
        val productsList = listOf(
            ProductDTO(
                1,
                "Nvidia GTX 1050",
                price = 1499.99,
                categoryId = 1,
            ),
            ProductDTO(
                2,
                "Nvidia GTX 3050",
                price = 2129.90,
                categoryId = 1
            ),
            ProductDTO(
                3,
                "Radeon RX 6700Xt",
                price = 3200.00,
                categoryId = 1
            ),
            ProductDTO(
                4,
                "AMD Ryzen 7 5800X",
                price = 2300.00,
                categoryId = 1
            ),
            ProductDTO(
                5,
                "Keyboard corsair",
                price = 249.90,
                categoryId = 2
            ),
            ProductDTO(
                6,
                "Monitor AOC gamer",
                price = 1299.90,
                categoryId = 2
            ),
            ProductDTO(
                7,
                "Corsair vengeance RAM memory",
                price = 349.90,
                categoryId = 1
            ),
            ProductDTO(
                8,
                "Corsair PLATINUM RAM memory",
                price = 549.99,
                categoryId = 1
            ),
            ProductDTO(
                9,
                "Corsair DDR2 RAM memory",
                price = 149.90,
                categoryId = 1
            ),
        )
    }
}