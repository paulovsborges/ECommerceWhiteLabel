package com.pvsb.ecommercewhitelabel.presentation.fragment.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
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
    private var selectedPaymentMethod = BILLET_LAYOUT

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
        setUpPaymentMethodLayout()
    }

    private fun setUpPaymentMethodLayout() {
        binding.apply {
            rbPixPayment.setOnClickListener {
                selectedPaymentMethod = PIX_LAYOUT
                vfPaymentMethod.displayedChild = PIX_LAYOUT
            }

            rbBilletPayment.setOnClickListener {
                selectedPaymentMethod = BILLET_LAYOUT
                vfPaymentMethod.displayedChild = BILLET_LAYOUT
            }
        }
    }

    companion object {
        const val BILLET_LAYOUT = 0
        const val PIX_LAYOUT = 1
    }
}