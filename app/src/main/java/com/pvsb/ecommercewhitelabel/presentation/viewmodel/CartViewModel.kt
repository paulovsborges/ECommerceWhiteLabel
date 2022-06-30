package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.core.firestore.model.CartProductsDTO
import com.pvsb.core.firestore.model.PopulateCartDTO
import com.pvsb.ecommercewhitelabel.domain.usecase.CartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val useCase: CartUseCase
) : ViewModel() {

    private val _initialCart = MutableLiveData<String>()
    val initialCart: LiveData<String> = _initialCart

    private val _addProductToCart = MutableLiveData<Boolean>()
    val addProductToCart: LiveData<Boolean> = _addProductToCart

    fun createCart(cart: PopulateCartDTO) {
        viewModelScope.launch {

            useCase.createCart(cart).collectLatest {
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
}