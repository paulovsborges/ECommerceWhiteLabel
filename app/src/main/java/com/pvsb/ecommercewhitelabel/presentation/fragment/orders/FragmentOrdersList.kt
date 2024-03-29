package com.pvsb.ecommercewhitelabel.presentation.fragment.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pvsb.core.model.OderModelReqDTO
import com.pvsb.core.model.OderModelResDTO
import com.pvsb.core.model.enums.OrderSituationEnum
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.databinding.FragmentOrdersListBinding
import com.pvsb.ecommercewhitelabel.presentation.adapter.OrdersAdapter

class FragmentOrdersList(
    private val situation: OrderSituationEnum,
    private val data: OderModelResDTO,
    onOrderLicked: (OderModelReqDTO) -> Unit
) : Fragment() {

    private var _binding: FragmentOrdersListBinding? = null
    private val binding get() = _binding!!
    private val mAdapter = OrdersAdapter(onOrderLicked)

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

    private fun initialSetUp() {
        binding.apply {
            rvOrders.adapter = mAdapter
            val filteredOrders = data.orders.filter { it.situation == getString(situation.label) }

            if (filteredOrders.isEmpty()) {
                vfMain.displayedChild = EMPTY_STATE
                val situationLabel = situation.label
                tvEmptyText.text =
                    getString(R.string.orders_empty_for_situation, getString(situationLabel))
            } else {
                vfMain.displayedChild = DATA_STATE
                mAdapter.submitList(filteredOrders)
            }
        }
    }

    companion object {
        const val DATA_STATE = 0
        const val EMPTY_STATE = 1
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
