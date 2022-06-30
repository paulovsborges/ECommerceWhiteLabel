package com.pvsb.ecommercewhitelabel.domain.usecase

import com.pvsb.core.firestore.model.CartProductsDTO
import com.pvsb.core.firestore.model.CreateCartDTO
import com.pvsb.core.firestore.model.PopulateCartDTO
import com.pvsb.ecommercewhitelabel.data.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CartUseCase @Inject constructor(
    private val repository: CartRepository
) {

    suspend fun createCart(cart: PopulateCartDTO): Flow<String> = flow {
        val id = System.currentTimeMillis().toString()
        populateCart(id, cart).collectLatest {
            if (it) emit(id)
        }
    }

    private suspend fun populateCart(cartId: String, cart: PopulateCartDTO): Flow<Boolean> = flow {
        val res = repository.populateCart(cartId, cart)
        emit(res)
    }

    suspend fun addProductToCart(cartId: String, product: CartProductsDTO): Flow<Boolean> = flow {
        val res = repository.addProductToCart(cartId, product)
        emit(res)
    }
}