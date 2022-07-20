package com.pvsb.ecommercewhitelabel.presentation.fragment.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.pvsb.core.model.enums.OrderSituationEnum
import com.pvsb.ecommercewhitelabel.databinding.FragmentOrdersListBinding
import com.pvsb.ecommercewhitelabel.presentation.adapter.OrdersViewPagerAdapter
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentOrdersList : Fragment() {

    private var _binding: FragmentOrdersListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialSetUp()
    }

    private fun setUpObservers(){

    }

    private fun initialSetUp() {
        binding.apply {
            vpOrders.adapter = OrdersViewPagerAdapter(requireActivity())
            TabLayoutMediator(tlOrdersType, vpOrders) { tab, pos ->
                when (pos) {
                    0 -> {
                        tab.text = getString(OrderSituationEnum.PAYMENT.label)
                    }
                    1 -> {
                        tab.text = getString(OrderSituationEnum.IN_TRANSPORT.label)
                    }
                    2 -> {
                        tab.text = getString(OrderSituationEnum.DELIVERY_ROUTE.label)
                    }
                    3 -> {
                        tab.text = getString(OrderSituationEnum.CONCLUDED.label)
                    }
                }
            }.attach()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}