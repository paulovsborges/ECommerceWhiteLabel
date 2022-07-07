package com.pvsb.ecommercewhitelabel.data.repository

import com.pvsb.core.firebase.model.ProductDTO

interface HomeRepository {

    suspend fun getProducts() : List<ProductDTO>
}