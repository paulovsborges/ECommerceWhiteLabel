package com.pvsb.ecommercewhitelabel.presentation.fragment.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pvsb.core.utils.onBackPress
import com.pvsb.ecommercewhitelabel.databinding.FragmentPaymentBinding

class FragmentPayment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

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