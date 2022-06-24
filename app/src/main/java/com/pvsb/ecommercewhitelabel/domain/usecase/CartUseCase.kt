package com.pvsb.ecommercewhitelabel.domain.usecase

import com.pvsb.core.firestore.model.CreateCartDTO
import com.pvsb.ecommercewhitelabel.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class CartUseCase @Inject constructor(
    private val repository: CartRepository
) {

    suspend fun createCart(): Flow<String> = flow {

        val cart = CreateCartDTO(
            id = UUID.randomUUID().toString(),
            totalPrice = 0.0,
            isActive = false,

            )
        val res = repository.createCart(cart)
        emit(res)
    }
}