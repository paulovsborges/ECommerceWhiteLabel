package com.pvsb.ecommercewhitelabel.domain.usecase

import com.pvsb.core.firestore.model.CartProductsDTO
import com.pvsb.core.firestore.model.PopulateCartDTO
import com.pvsb.core.utils.ResponseState
import com.pvsb.ecommercewhitelabel.data.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CartUseCase @Inject constructor(
    private val repository: CartRepository
) {

    suspend fun createCart(cart: PopulateCartDTO): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)
        val id = System.currentTimeMillis().toString()
        createCart(id, cart).first {
            if (it) emit(ResponseState.Complete.Success(id))
            true
        }
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }

    private suspend fun createCart(cartId: String, cart: PopulateCartDTO): Flow<Boolean> = flow {
        val res = repository.createCart(cartId, cart)
        emit(res)
    }

    suspend fun addProductToCart(cartId: String, product: CartProductsDTO): Flow<ResponseState> =
        flow {
            emit(ResponseState.Loading)
            val res = repository.addProductToCart(cartId, product)
            emit(ResponseState.Complete.Success(res))
        }.catch {
            emit(ResponseState.Complete.Fail(it))
        }

    suspend fun getCartContent(cartId: String): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)
        val res = repository.getCartContent(cartId)
        emit(ResponseState.Complete.Success(res))
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }

    suspend fun deleteProduct(cartId: String, product: CartProductsDTO): Flow<ResponseState> =
        flow {
            emit(ResponseState.Loading)
            val res = repository.deleteProduct(cartId, product)
            emit(ResponseState.Complete.Success(res))
        }.catch {
            emit(ResponseState.Complete.Fail(it))
        }
}