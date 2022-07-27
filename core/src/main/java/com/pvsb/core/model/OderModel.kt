package com.pvsb.core.model

import com.google.firebase.firestore.PropertyName
import kotlinx.serialization.Serializable

data class OderModelResDTO(
    var orders: List<OderModelReqDTO> = emptyList()
)

@Serializable
data class OderModelReqDTO(
    var orderId: String = "",
    @get:PropertyName("delivery_info")
    @set:PropertyName("delivery_info")
    var deliveryInfo: UserAddressDTO = UserAddressDTO(),
    var products: List<CartProductsDTO> = emptyList(),
    var paymentInfo: OrderPaymentInfoDTO = OrderPaymentInfoDTO(),
    var situation: String = "",
    var date: String = ""
)

@Serializable
data class OrderPaymentInfoDTO(
    @get:PropertyName("payment_method")
    @set:PropertyName("payment_method")
    var paymentMethod: String = "",
    @get:PropertyName("order_value")
    @set:PropertyName("order_value")
    var orderValue: Double = 0.0,
)
