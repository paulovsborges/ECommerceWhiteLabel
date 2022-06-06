package com.pvsb.ecommercewhitelabel.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pvsb.ecommercewhitelabel.data.model.MainHomeModel
import com.pvsb.ecommercewhitelabel.databinding.FragmentProductDetailsBinding

class FragmentProductDetails : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val test = arguments?.get("itemTest") as MainHomeModel

        binding.apply {
            tvProductPrice.text = test.price
            tvProductTitle.text = test.title
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}