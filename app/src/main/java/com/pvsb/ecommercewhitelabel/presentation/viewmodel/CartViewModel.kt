package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.core.firebase.model.CartProductsDTO
import com.pvsb.core.firebase.model.PopulateCartDTO
import com.pvsb.core.utils.ResponseState
import com.pvsb.ecommercewhitelabel.domain.usecase.CartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val useCase: CartUseCase
) : ViewModel() {

    private val _initialCart = MutableStateFlow<ResponseState>(ResponseState.Init)
    val initialCart: StateFlow<ResponseState> = _initialCart

    private val _addProductToCart = MutableStateFlow<ResponseState>(ResponseState.Init)
    val addProductToCart: StateFlow<ResponseState> = _addProductToCart

    private val _cartContent = MutableStateFlow<ResponseState>(ResponseState.Init)
    val cartContent: StateFlow<ResponseState> = _cartContent

    fun createCart(cartId: String, cart: PopulateCartDTO) {
        viewModelScope.launch {
            useCase.createCart(cartId, cart).collectLatest {
                _initialCart.value = it
            }
        }
    }

    fun addProductToCart(cartId: String, product: CartProductsDTO) {
        viewModelScope.launch {
            useCase.addProductToCart(cartId, product).collectLatest {
                _addProductToCart.value = it
            }
        }
    }

    fun getCartContent(cartId: String) {
        viewModelScope.launch {
            useCase.getCartContent(cartId)
                .collectLatest {
                    _cartContent.value = it
                }
        }
    }

    fun deleteProduct(cartId: String, product: CartProductsDTO) {
        viewModelScope.launch {
            useCase.deleteProduct(cartId, product)
                .collectLatest {
                    getCartContent(cartId)
                }
        }
    }
}