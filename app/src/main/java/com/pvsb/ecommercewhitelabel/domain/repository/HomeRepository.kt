package com.pvsb.ecommercewhitelabel.domain.repository

import com.pvsb.core.firestore.model.ProductDTO

interface HomeRepository {

    suspend fun getProducts() : List<ProductDTO>
}