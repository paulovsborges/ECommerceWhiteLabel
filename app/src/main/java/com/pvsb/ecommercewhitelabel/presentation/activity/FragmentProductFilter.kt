package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.pvsb.core.model.ProductFilters
import com.pvsb.core.model.ProductFiltersPrice
import com.pvsb.core.utils.onBackPress
import com.pvsb.core.utils.popBackStack
import com.pvsb.core.utils.setResultToFragmentListener
import com.pvsb.ecommercewhitelabel.databinding.FragmentProductFiltersBinding
import com.pvsb.ecommercewhitelabel.presentation.adapter.ProductFilterAdapter
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.FiltersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentProductFilter : Fragment() {

    private var _binding: FragmentProductFiltersBinding? = null
    private val binding get() = _binding!!
    private val mAdapter = ProductFilterAdapter(::onFilterSelected)
    private val viewModel: FiltersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialSetup()
    }

    private fun initialSetup() {
        binding.rvMain.adapter = mAdapter

        binding.btnApplyFilters.setOnClickListener {

            val obj = ProductFilters(
                price = ProductFiltersPrice(
                    minValue = 10.00,
                    maxValue = 20.00
                ),
                "blabla"
            )

            setResultToFragmentListener(obj, "bundle_key")

//            setFragmentResult("request_key", bundleOf("bundle_key" to "test string"))
            popBackStack()
        }
    }

    private fun onFilterSelected(item: ProductFilters) {
        viewModel.handleFilterSelection(item)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}