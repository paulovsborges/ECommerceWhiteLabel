package com.pvsb.ecommercewhitelabel.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pvsb.core.model.OderModelReqDTO
import com.pvsb.core.model.OderModelResDTO
import com.pvsb.core.model.enums.OrderSituationEnum
import com.pvsb.ecommercewhitelabel.presentation.fragment.orders.FragmentOrdersList

class OrdersViewPagerAdapter(
    activity: FragmentActivity,
    private val data: OderModelResDTO,
    private val onOrderLicked: (OderModelReqDTO) -> Unit
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragmentOrdersList(OrderSituationEnum.PAYMENT, data, onOrderLicked)
            }
            1 -> {
                FragmentOrdersList(OrderSituationEnum.IN_TRANSPORT, data, onOrderLicked)
            }
            2 -> {
                FragmentOrdersList(OrderSituationEnum.DELIVERY_ROUTE, data, onOrderLicked)
            }
            else -> {
                FragmentOrdersList(OrderSituationEnum.CONCLUDED, data, onOrderLicked)
            }
        }
    }
}
