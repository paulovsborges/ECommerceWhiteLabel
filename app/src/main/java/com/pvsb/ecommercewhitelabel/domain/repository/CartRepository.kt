package com.pvsb.ecommercewhitelabel.domain.repository

import com.pvsb.core.firestore.model.CreateCartDTO
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun createCart(cart: CreateCartDTO): String
}