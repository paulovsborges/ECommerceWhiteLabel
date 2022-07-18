package com.pvsb.ecommercewhitelabel.presentation.fragment.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pvsb.core.model.enums.PaymentType
import com.pvsb.core.utils.formatCurrency
import com.pvsb.core.utils.onBackPress
import com.pvsb.core.utils.switchFragment
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
        initialSetup()
    }

    private fun initialSetup() {
        binding.apply {
            ivBack.setOnClickListener {
                onBackPress()
            }

            btnNext.setOnClickListener {
                switchFragment(FragmentPaymentConfirmation(), saveBackStack = true)
            }

            tvTotalValue.text = hostViewModel.cartObj?.total?.formatCurrency()
        }
        setUpPaymentMethodLayout()
    }

    private fun setUpPaymentMethodLayout() {
        binding.apply {
            rbPixPayment.setOnClickListener {
                hostViewModel.selectedPaymentMethod = PaymentType.PIX
                vfPaymentMethod.displayedChild = PaymentType.PIX.ordinal
            }

            rbBilletPayment.setOnClickListener {
                hostViewModel.selectedPaymentMethod = PaymentType.BILLET
                vfPaymentMethod.displayedChild = PaymentType.BILLET.ordinal
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}