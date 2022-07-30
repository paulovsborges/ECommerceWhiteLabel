package com.pvsb.core.model.enums

import androidx.annotation.StringRes
import com.pvsb.core.R

enum class OrderSituationEnum(@StringRes val label: Int) {
    PAYMENT(R.string.orders_type_payment_label),
    IN_TRANSPORT(R.string.orders_type_transport_label),
    DELIVERY_ROUTE(R.string.orders_type_delivery_label),
    CONCLUDED(R.string.orders_type_concluded_label)
}
