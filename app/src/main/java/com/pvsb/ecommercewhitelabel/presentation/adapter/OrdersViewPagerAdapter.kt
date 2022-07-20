package com.pvsb.ecommercewhitelabel.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pvsb.core.model.OderModelResDTO
import com.pvsb.core.model.enums.OrderSituationEnum
import com.pvsb.ecommercewhitelabel.presentation.fragment.orders.FragmentOrderListType

class OrdersViewPagerAdapter(
    activity: FragmentActivity,
    private val data: OderModelResDTO
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragmentOrderListType(OrderSituationEnum.PAYMENT, data)
            }
            1 -> {
                FragmentOrderListType(OrderSituationEnum.IN_TRANSPORT, data)
            }
            2 -> {
                FragmentOrderListType(OrderSituationEnum.DELIVERY_ROUTE, data)
            }
            else -> {
                FragmentOrderListType(OrderSituationEnum.CONCLUDED, data)
            }
        }
    }
}