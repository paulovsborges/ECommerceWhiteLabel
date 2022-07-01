package com.pvsb.ecommercewhitelabel.presentation.fragment.payment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pvsb.core.utils.popBackStack
import com.pvsb.core.utils.switchFragment
import com.pvsb.ecommercewhitelabel.databinding.FragmentSelectAddressBinding

class FragmentSelectAddress : Fragment() {

    private var _binding: FragmentSelectAddressBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialSetUp()
    }

    private fun initialSetUp() {
        binding.apply {
            ivBack.setOnClickListener {
                popBackStack()
            }

            btnContinue.setOnClickListener {
                switchFragment(FragmentPayment())
            }
        }
    }
}