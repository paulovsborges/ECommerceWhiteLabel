package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.core.model.OderModelReqDTO
import com.pvsb.core.model.OrderPaymentInfoDTO
import com.pvsb.core.model.PopulateCartDTO
import com.pvsb.core.model.UserAddressDTO
import com.pvsb.core.model.enums.PaymentType
import com.pvsb.core.utils.ResponseState
import com.pvsb.core.utils.handleResponseState
import com.pvsb.ecommercewhitelabel.domain.usecase.CartUseCase
import com.pvsb.ecommercewhitelabel.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val cartUseCase: CartUseCase,
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    var selectedAddress: UserAddressDTO? = null
    var selectedPaymentMethod: PaymentType = PaymentType.BILLET
    var cartObj: PopulateCartDTO? = null

    private val _registerPayment = MutableStateFlow<ResponseState>(ResponseState.Init)
    val registerPayment: StateFlow<ResponseState> = _registerPayment

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

    fun registerOder(userId: String) {
        viewModelScope.launch {
            selectedAddress?.let { address ->
                cartObj?.let { cart ->

                    val req = OderModelReqDTO(
                        deliveryInfo = address,
                        products = cart.products,
                        paymentInfo = OrderPaymentInfoDTO(
                            paymentMethod = selectedPaymentMethod.label,
                            orderValue = cart.total
                        )
                    )

                    profileUseCase.registerOder(userId, req).collect {
                        _registerPayment.value = it
                    }
                }
            }
        }
    }
}