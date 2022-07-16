package com.pvsb.ecommercewhitelabel.presentation.fragment.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pvsb.ecommercewhitelabel.databinding.FragmentCreateAddressBinding
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentCreateAddress : Fragment() {

    private var _binding: FragmentCreateAddressBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialSetUp()
    }

    private fun initialSetUp() {
        viewModel.getPostalCodeInfo("89012510")
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}