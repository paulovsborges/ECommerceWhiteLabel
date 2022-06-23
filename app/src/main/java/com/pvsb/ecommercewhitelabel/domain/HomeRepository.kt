package com.pvsb.ecommercewhitelabel.domain

import com.pvsb.ecommercewhitelabel.data.model.ProductDTO

interface HomeRepository {

    suspend fun getProducts() : List<ProductDTO>
}