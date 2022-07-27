package com.pvsb.ecommercewhitelabel.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pvsb.core.model.ProductFilterCategories
import com.pvsb.core.utils.Constants.FILTERS_BUNDLE_KEY
import com.pvsb.core.utils.MockFactory.Filters.categoriesList
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
    private val viewModel: FiltersViewModel by activityViewModels()

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
        binding.apply {
            ivBack.setOnClickListener { popBackStack() }
            rvMain.adapter = mAdapter
            mAdapter.submitList(categoriesList)
            btnApplyFilters.setOnClickListener { getFiltersAndGoBack() }
            tvCleanFilters.setOnClickListener { cleanFilters() }
        }
    }

    private fun cleanFilters() {
        binding.apply {
            tiMinValue.editText?.text?.clear()
            tiMaxValue.editText?.text?.clear()
            mAdapter.currentList.forEach {
                it.isChecked = false
            }
        }

        viewModel.cleanFilters()
        getFiltersAndGoBack()
    }

    private fun getFiltersAndGoBack() {
        binding.apply {
            val minValue = if (tiMinValue.editText?.text.toString().isEmpty()) {
                0.0
            } else {
                tiMinValue.editText?.text.toString().toDouble()
            }

            val maxValue = if (tiMaxValue.editText?.text.toString().isEmpty()) {
                0.0
            } else {
                tiMaxValue.editText?.text.toString().toDouble()
            }

            viewModel.minValue = minValue
            viewModel.maxValue = maxValue
            setResultToFragmentListener(true, FILTERS_BUNDLE_KEY)
            popBackStack()
        }
    }

    private fun onFilterSelected(item: ProductFilterCategories) {
        viewModel.handleFilterSelection(item)
    }

    private fun setUpAlreadyAppliedFilters() {
        binding.apply {
            if (viewModel.minValue > 0.0) {
                tiMinValue.editText?.setText(viewModel.minValue.toString())
            }
            if (viewModel.maxValue > 0.0) {
                tiMaxValue.editText?.setText(viewModel.maxValue.toString())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setUpAlreadyAppliedFilters()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}