package com.pvsb.ecommercewhitelabel.presentation.fragment.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pvsb.core.model.OderModelReqDTO
import com.pvsb.core.utils.formatCurrency
import com.pvsb.ecommercewhitelabel.databinding.FragmentOrderDetailsBinding

class FragmentOrderDetails : Fragment() {

    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!
    private var details: OderModelReqDTO? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        details = arguments?.getParcelable("ITEM_DETAILS")
        initialSetUp()
    }

    private fun initialSetUp() {
        binding.apply {
            details?.let {
                tvOrderItemsSize.text = it.products.size.toString()
                tvOrderPaymentMethod.text = it.paymentInfo.paymentMethod
                tvOrderTotalValue.text = it.paymentInfo.orderValue.formatCurrency()
                tvAddress.text = "${it.deliveryInfo.street}, ${it.deliveryInfo.number}"
                tvNeighbour.text = it.deliveryInfo.neighbour
                tvCity.text = it.deliveryInfo.city
                tvZipCode.text = it.deliveryInfo.zipCode
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}