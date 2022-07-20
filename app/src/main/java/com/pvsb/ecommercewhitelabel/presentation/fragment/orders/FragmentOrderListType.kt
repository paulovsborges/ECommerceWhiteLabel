package com.pvsb.ecommercewhitelabel.presentation.fragment.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pvsb.core.model.OderModelResDTO
import com.pvsb.core.model.enums.OrderSituationEnum
import com.pvsb.ecommercewhitelabel.databinding.FragmentOrderListTypeBinding
import com.pvsb.ecommercewhitelabel.presentation.adapter.OrdersAdapter

class FragmentOrderListType(
    private val situation: OrderSituationEnum,
    private val data: OderModelResDTO
) : Fragment() {

    private var _binding: FragmentOrderListTypeBinding? = null
    private val binding get() = _binding!!
    private val mAdapter = OrdersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderListTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialSetUp()
    }

    private fun initialSetUp() {
        binding.apply {
            rvOrders.adapter = mAdapter
        }

        mAdapter.submitList(data.orders.filter { it.situation == getString(situation.label) })
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}