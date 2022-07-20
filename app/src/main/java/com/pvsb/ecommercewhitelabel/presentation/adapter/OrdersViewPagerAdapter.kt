package com.pvsb.ecommercewhitelabel.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pvsb.core.model.enums.OrderSituationEnum
import com.pvsb.ecommercewhitelabel.presentation.fragment.orders.FragmentOrderListType

class OrdersViewPagerAdapter(activity: FragmentActivity) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragmentOrderListType(OrderSituationEnum.PAYMENT)
            }
            1 -> {
                FragmentOrderListType(OrderSituationEnum.IN_TRANSPORT)
            }
            2 -> {
                FragmentOrderListType(OrderSituationEnum.DELIVERY_ROUTE)
            }
            else -> {
                FragmentOrderListType(OrderSituationEnum.CONCLUDED)
            }
        }
    }
}