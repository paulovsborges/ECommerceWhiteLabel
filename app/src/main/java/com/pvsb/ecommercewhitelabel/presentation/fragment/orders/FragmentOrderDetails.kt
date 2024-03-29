package com.pvsb.ecommercewhitelabel.presentation.fragment.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pvsb.core.model.OderModelReqDTO
import com.pvsb.core.utils.deserializeObjFromArguments
import com.pvsb.core.utils.formatCurrency
import com.pvsb.core.utils.formatDate
import com.pvsb.core.utils.onBackPress
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.databinding.FragmentOrderDetailsBinding
import com.pvsb.ecommercewhitelabel.presentation.adapter.CartProductsAdapter

class FragmentOrderDetails : Fragment() {

    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!
    private var details: OderModelReqDTO? = null
    private val mAdapter = CartProductsAdapter {}

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
        details = deserializeObjFromArguments()
        initialSetUp()
    }

    private fun initialSetUp() {
        binding.apply {
            details?.let {
                tvOrderItemsSize.text = it.products.size.toString()
                tvOrderPaymentMethod.text = it.paymentInfo.paymentMethod
                tvOrderTotalValue.text = it.paymentInfo.orderValue.formatCurrency()
                tvAddress.text =
                    getString(
                        R.string.orders_order_details_delivery_address_placeholder,
                        it.deliveryInfo.street, it.deliveryInfo.number
                    )
                tvNeighbour.text = it.deliveryInfo.neighbour
                tvCity.text = it.deliveryInfo.city
                tvZipCode.text = it.deliveryInfo.zipCode
                tvOrderDate.text = it.date.formatDate()
                tvOrderId.text = getString(R.string.orders_list_item_order_label, it.orderId)
                mAdapter.submitList(it.products)
            }

            rvProducts.adapter = mAdapter

            ivBack.setOnClickListener {
                onBackPress()
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
