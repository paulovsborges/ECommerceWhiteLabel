package com.pvsb.ecommercewhitelabel.presentation.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.pvsb.core.model.ProductDTO
import com.pvsb.core.model.ProductFilters
import com.pvsb.core.utils.*
import com.pvsb.ecommercewhitelabel.databinding.FragmentSearchBinding
import com.pvsb.ecommercewhitelabel.presentation.activity.ActivityProductDetails
import com.pvsb.ecommercewhitelabel.presentation.activity.FragmentProductFilter
import com.pvsb.ecommercewhitelabel.presentation.adapter.HomeAdapter
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.FiltersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FragmentSearch : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val mAdapter = HomeAdapter(::navigateToDetails)
    private val viewModel: FiltersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialSetUp()
    }

    private fun initialSetUp() {
        binding.apply {
            rvProductsSearch.adapter = mAdapter
            ivFilters.setOnClickListener {
                openFilters()
            }

            tiSearch.editText?.setOnEditorActionListener(object :
                TextView.OnEditorActionListener {

                override fun onEditorAction(
                    v: TextView?,
                    actionId: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                        val search = tiSearch.editText?.text.toString()
                        if (search.isNotEmpty()) {
                            setUpSearch(search)
                        }
                        return true
                    }
                    return false
                }
            })

            tiSearch.editText?.doAfterTextChanged {
                if ((tiSearch.editText?.text.toString().isEmpty())) {
                    getProducts()
                }
            }
        }
        getProducts()
    }

    private fun getProducts() {
        viewModel.getProducts()
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                handleResponse<List<ProductDTO>>(state,
                    onSuccess = {
                        viewModel.products.addAll(it)
                        mAdapter.submitList(it)
                    },
                    onEmpty = {

                    },
                    onError = {

                    })
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setUpSearch(search: String) {
        viewModel.searchProducts(search)
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                handleResponse<List<ProductDTO>>(state,
                    onSuccess = {
                        mAdapter.submitList(it)
                    },
                    onEmpty = {

                    },
                    onError = {

                    })
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun openFilters() {
        switchFragment(FragmentProductFilter(), saveBackStack = true)
        getValueFromFragmentListener<ProductFilters>("bundle_key") {
            Toast.makeText(context, it.price.maxValue.formatCurrency(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToDetails(item: ProductDTO) {
        requireContext().openActivity(ActivityProductDetails::class.java) {
            it.putExtra(Constants.PRODUCT_DETAILS, item)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}