package com.pvsb.ecommercewhitelabel.presentation.fragment.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pvsb.core.utils.formatLength
import com.pvsb.ecommercewhitelabel.databinding.FragmentPaymentConfirmationBinding
import com.pvsb.ecommercewhitelabel.presentation.adapter.PaymentConfirmationProductsAdapter
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPaymentConfirmation : Fragment() {

    private var _binding: FragmentPaymentConfirmationBinding? = null
    private val binding get() = _binding!!
    private val hostViewModel: PaymentViewModel by activityViewModels()
    private val productsAdapter = PaymentConfirmationProductsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialSetUp()
    }

    private fun initialSetUp() {
        setUpData()
        binding.apply {
            rvProducts.adapter = productsAdapter
        }
    }

    private fun setUpData() {
        productsAdapter.submitList(hostViewModel.cartObj?.products)

        binding.apply {
            tvCity.text = hostViewModel.selectedAddress?.city?.formatLength()
            tvZipCode.text = hostViewModel.selectedAddress?.zipCode?.formatLength()
            tvStreet.text = hostViewModel.selectedAddress?.street?.formatLength()
            tvNeighbour.text = hostViewModel.selectedAddress?.neighbour?.formatLength()
            tvNumber.text = hostViewModel.selectedAddress?.number?.formatLength()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}