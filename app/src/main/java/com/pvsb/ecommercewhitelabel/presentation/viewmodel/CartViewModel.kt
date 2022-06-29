package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.ecommercewhitelabel.domain.usecase.CartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val useCase: CartUseCase
) : ViewModel() {

    private val _cartId = MutableLiveData<String>()
    val cartId: LiveData<String> = _cartId

    fun createCart() {
        viewModelScope.launch {

            useCase.createCart().collectLatest {
                _cartId.value = it
            }
        }
    }

    fun populateCart(productId: String, cartId: String) {
        viewModelScope.launch {

        }
    }
}