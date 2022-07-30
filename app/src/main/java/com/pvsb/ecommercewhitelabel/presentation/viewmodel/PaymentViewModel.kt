package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.pvsb.core.model.OderModelReqDTO
import com.pvsb.core.model.OrderPaymentInfoDTO
import com.pvsb.core.model.PopulateCartDTO
import com.pvsb.core.model.UserAddressDTO
import com.pvsb.core.model.enums.PaymentType
import com.pvsb.core.utils.CoroutineViewModel
import com.pvsb.core.utils.ResponseState
import com.pvsb.core.utils.buildStateFlow
import com.pvsb.core.utils.handleResponseState
import com.pvsb.ecommercewhitelabel.domain.usecase.CartUseCase
import com.pvsb.ecommercewhitelabel.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val cartUseCase: CartUseCase,
    private val profileUseCase: ProfileUseCase
) : CoroutineViewModel() {

    var selectedAddress: UserAddressDTO? = null
    var selectedPaymentMethod: PaymentType = PaymentType.BILLET
    var cartObj: PopulateCartDTO? = null

    fun getCartContent(cartId: String) {
        cartUseCase.getCartContent(cartId)
            .onEach { state ->
                state.handleResponseState<PopulateCartDTO>(
                    onLoading = {},
                    onSuccess = {
                        cartObj = it
                    },
                    onError = {}
                )
            }.launchIn(viewModelScope)
    }

    fun registerOder(cartId: String, userId: String, situation: String): StateFlow<ResponseState>? {
        selectedAddress?.let { address ->
            cartObj?.let { cart ->

                val req = OderModelReqDTO(
                    deliveryInfo = address,
                    products = cart.products,
                    paymentInfo = OrderPaymentInfoDTO(
                        paymentMethod = selectedPaymentMethod.label,
                        orderValue = cart.total
                    ),
                    situation = situation,
                    date = Calendar.getInstance().time.toString()
                )

                return buildStateFlow(profileUseCase.registerOder(cartId, userId, req))
            }
        }

        return null
    }
}
