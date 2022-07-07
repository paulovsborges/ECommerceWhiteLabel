package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.core.firestore.model.CartProductsDTO
import com.pvsb.core.firestore.model.PopulateCartDTO
import com.pvsb.core.utils.ResponseState
import com.pvsb.ecommercewhitelabel.domain.usecase.CartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val useCase: CartUseCase
) : ViewModel() {

    private val _initialCart = MutableStateFlow<ResponseState>(ResponseState.Init)
    val initialCart: StateFlow<ResponseState> = _initialCart

    private val _addProductToCart = MutableLiveData<Boolean>()
    val addProductToCart: LiveData<Boolean> = _addProductToCart

    private val _cartContent = MutableLiveData<PopulateCartDTO>()
    val cartContent: LiveData<PopulateCartDTO> = _cartContent

    fun createCart(cart: PopulateCartDTO) {
        viewModelScope.launch {

            useCase.createCart(cart).collectLatest {
                _initialCart.value = it
            }
        }
    }

    fun addProductToCart(cartId: String, product: CartProductsDTO) {
        viewModelScope.launch {
            useCase.addProductToCart(cartId, product)
                .catch {
                    Log.d("cartVM", "${it.message}")
                }
                .collectLatest {
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

                .catch {
                    Log.d("DELETE_PRODUCT", "failure $it")
                }
                .collectLatest {
                    getCartContent(cartId)
                }
        }
    }
}