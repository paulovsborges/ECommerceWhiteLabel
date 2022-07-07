package com.pvsb.ecommercewhitelabel.data.repository

import com.pvsb.core.firestore.model.CartProductsDTO
import com.pvsb.core.firestore.model.PopulateCartDTO

interface CartRepository {

    suspend fun createCart(cartId: String, cart: PopulateCartDTO, value: Double): Boolean

    suspend fun addProductToCart(cartId: String, product: CartProductsDTO): Boolean

    suspend fun getCartContent(cartId: String): PopulateCartDTO

    suspend fun deleteProduct(cartId: String, product: CartProductsDTO): Boolean
}