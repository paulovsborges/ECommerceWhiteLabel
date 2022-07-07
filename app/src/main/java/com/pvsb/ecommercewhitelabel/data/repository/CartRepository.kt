package com.pvsb.ecommercewhitelabel.data.repository

import com.pvsb.core.firebase.model.CartProductsDTO
import com.pvsb.core.firebase.model.PopulateCartDTO

interface CartRepository {

    suspend fun createCart(cartId: String, cart: PopulateCartDTO, value: Double): Boolean

    suspend fun addProductToCart(cartId: String, product: CartProductsDTO, value: Double): Boolean

    suspend fun getCartContent(cartId: String): PopulateCartDTO

    suspend fun deleteProduct(cartId: String, product: CartProductsDTO, value: Double): Boolean
}