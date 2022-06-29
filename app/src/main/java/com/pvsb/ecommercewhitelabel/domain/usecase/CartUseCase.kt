package com.pvsb.ecommercewhitelabel.domain.usecase

import com.pvsb.core.firestore.model.CreateCartDTO
import com.pvsb.ecommercewhitelabel.data.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CartUseCase @Inject constructor(
    private val repository: CartRepository
) {

    suspend fun createCart(): Flow<String> = flow {

        val id  = System.currentTimeMillis().toString()

        emit(id)
    }

    suspend fun populateCart(cartId: String, cart: CreateCartDTO){

    }
}