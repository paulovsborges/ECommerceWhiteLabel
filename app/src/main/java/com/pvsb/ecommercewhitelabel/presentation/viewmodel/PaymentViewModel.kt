package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.pvsb.core.model.UserAddressDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor() : ViewModel() {
    var selectedAddress: UserAddressDTO? = null
    var selectedPaymentMethod: Int = 0
}