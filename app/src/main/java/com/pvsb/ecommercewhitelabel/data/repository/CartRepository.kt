package com.pvsb.ecommercewhitelabel.data.repository

import com.pvsb.core.firestore.model.CreateCartDTO
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun populateCart(cartId: String, cart: CreateCartDTO): String
}