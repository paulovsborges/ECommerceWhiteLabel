package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import com.pvsb.core.model.CartProductsDTO
import com.pvsb.core.model.PopulateCartDTO
import com.pvsb.core.utils.CoroutineViewModel
import com.pvsb.core.utils.ResponseState
import com.pvsb.core.utils.buildStateFlow
import com.pvsb.ecommercewhitelabel.domain.usecase.CartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val useCase: CartUseCase
) : CoroutineViewModel() {

    fun createCart(cartId: String, cart: PopulateCartDTO): StateFlow<ResponseState> =
        buildStateFlow(useCase.createCart(cartId, cart))

    fun addProductToCart(cartId: String, product: CartProductsDTO): StateFlow<ResponseState> =
        buildStateFlow(useCase.addProductToCart(cartId, product))

    fun getCartContent(cartId: String): StateFlow<ResponseState> =
        buildStateFlow(useCase.getCartContent(cartId))

    fun deleteProduct(cartId: String, product: CartProductsDTO): StateFlow<ResponseState> =
        buildStateFlow(useCase.deleteProduct(cartId, product))
}
