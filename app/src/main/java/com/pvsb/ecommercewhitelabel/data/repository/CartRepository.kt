package com.pvsb.ecommercewhitelabel.data.repository

import com.pvsb.core.firestore.model.CartProductsDTO
import com.pvsb.core.firestore.model.CreateCartDTO
import com.pvsb.core.firestore.model.PopulateCartDTO
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun createCart(cartId: String, cart: PopulateCartDTO): Boolean

    suspend fun addProductToCart(cartId: String, product: CartProductsDTO): Boolean
}