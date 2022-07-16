package com.pvsb.ecommercewhitelabel.presentation.fragment.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pvsb.ecommercewhitelabel.databinding.FragmentAddressesListBinding

class FragmentAddressesList : Fragment() {

    private var _binding: FragmentAddressesListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}