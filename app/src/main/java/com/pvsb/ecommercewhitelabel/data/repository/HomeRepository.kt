package com.pvsb.ecommercewhitelabel.data.repository

import com.pvsb.core.model.ProductDTO

interface HomeRepository {

    suspend fun getProducts(): List<ProductDTO>
}
