package com.pvsb.ecommercewhitelabel.domain.usecase

import com.pvsb.core.firestore.model.CreateCartDTO
import com.pvsb.ecommercewhitelabel.domain.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

class CartUseCase @Inject constructor(
    private val repository: CartRepository
) {

    suspend fun createCart(): Flow<String> = flow {

        val cart = CreateCartDTO(
            id = UUID.randomUUID().toString()
        )

        val res = repository.createCart(cart)

        emit(res)
    }.flowOn(Dispatchers.Unconfined)
}