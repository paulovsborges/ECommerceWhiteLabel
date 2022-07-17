package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.core.model.PopulateCartDTO
import com.pvsb.core.model.UserAddressDTO
import com.pvsb.core.utils.ResponseState
import com.pvsb.core.utils.handleResponseState
import com.pvsb.ecommercewhitelabel.domain.usecase.CartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val cartUseCase: CartUseCase
) : ViewModel() {
    var selectedAddress: UserAddressDTO? = null
    var selectedPaymentMethod: Int = 0
    var cartObj: PopulateCartDTO? = null

    fun getCartContent(cartId: String) {
        viewModelScope.launch {
            cartUseCase.getCartContent(cartId).collect { state ->
                state.handleResponseState<PopulateCartDTO>(
                    onLoading = {},
                    onSuccess = {
                        cartObj = it
                    },
                    onError = {})
            }
        }
    }
}