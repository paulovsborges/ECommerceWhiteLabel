package com.pvsb.ecommercewhitelabel.domain.usecase

import com.pvsb.core.firestore.model.CartProductsDTO
import com.pvsb.core.firestore.model.PopulateCartDTO
import com.pvsb.ecommercewhitelabel.data.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CartUseCase @Inject constructor(
    private val repository: CartRepository
) {

    suspend fun createCart(cart: PopulateCartDTO): Flow<String> = flow {
        val id = System.currentTimeMillis().toString()
        createCart(id, cart).first {
            if (it) emit(id)
            true
        }
    }

    private suspend fun createCart(cartId: String, cart: PopulateCartDTO): Flow<Boolean> = flow {
        val res = repository.createCart(cartId, cart)
        emit(res)
    }

    suspend fun addProductToCart(cartId: String, product: CartProductsDTO): Flow<Boolean> = flow {
        val res = repository.addProductToCart(cartId, product)
        emit(res)
    }

    suspend fun getCartContent(cartId: String): Flow<PopulateCartDTO> = flow{
        val res = repository.getCartContent(cartId)
        emit(res)
    }
}