package com.pvsb.ecommercewhitelabel.presentation.fragment.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pvsb.core.utils.onBackPress
import com.pvsb.ecommercewhitelabel.databinding.FragmentPaymentBinding
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPayment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private val hostViewModel: PaymentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(context, hostViewModel.selectedAddress?.addressNick, Toast.LENGTH_SHORT)
            .show()

        initialSetup()
    }

    private fun initialSetup() {
        binding.apply {
            ivBack.setOnClickListener {
                onBackPress()
            }
        }
    }
}