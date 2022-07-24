package com.pvsb.ecommercewhitelabel.domain.usecase

import com.pvsb.core.model.CartProductsDTO
import com.pvsb.core.model.PopulateCartDTO
import com.pvsb.core.utils.ResponseState
import com.pvsb.ecommercewhitelabel.data.repository.CartRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CartUseCase @Inject constructor(
    private val repository: CartRepository
) {

    fun createCart(cartId: String, cart: PopulateCartDTO): Flow<ResponseState> = flow {

        emit(ResponseState.Loading)
        val product = cart.products.first()
        val valueToIncrement = product.amount * product.product.price
        val cartCreated = repository.createCart(cartId, cart, valueToIncrement)

        if (cartCreated) {
            emit(ResponseState.Complete.Success(cartId))
        }
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }

    fun addProductToCart(cartId: String, product: CartProductsDTO): Flow<ResponseState> =
        flow {
            emit(ResponseState.Loading)
            val valueToIncrement = product.amount * product.product.price
            val res = repository.addProductToCart(cartId, product, valueToIncrement)
            emit(ResponseState.Complete.Success(res))
        }.catch {
            emit(ResponseState.Complete.Fail(it))
        }

    fun getCartContent(cartId: String): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)
        val res = repository.getCartContent(cartId)

        if (res.products.isEmpty()) {
            emit(ResponseState.Complete.Empty)
        } else {
            emit(ResponseState.Complete.Success(res))
        }
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }

    fun deleteProduct(cartId: String, product: CartProductsDTO): Flow<ResponseState> =
        flow {
            emit(ResponseState.Loading)
            val valueToRemove = product.amount * product.product.price
            val res = repository.deleteProduct(cartId, product, valueToRemove)
            emit(ResponseState.Complete.Success(res))
        }.catch {
            emit(ResponseState.Complete.Fail(it))
        }
}