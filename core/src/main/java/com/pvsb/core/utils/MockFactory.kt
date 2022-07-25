package com.pvsb.core.utils

import com.pvsb.core.model.ProductDTO
import com.pvsb.core.model.ProductFilters

sealed class MockFactory {

    object Filters {
        val list = emptyList<ProductDTO>()
    }
}