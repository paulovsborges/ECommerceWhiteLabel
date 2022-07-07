package com.pvsb.ecommercewhitelabel.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pvsb.core.firestore.model.CartProductsDTO
import com.pvsb.core.firestore.model.PopulateCartDTO
import com.pvsb.core.utils.Constants.CART_ID
import com.pvsb.core.utils.formatCurrency
import com.pvsb.core.utils.getValueDS
import com.pvsb.core.utils.handleResponse
import com.pvsb.core.utils.openActivity
import com.pvsb.ecommercewhitelabel.databinding.FragmentCartBinding
import com.pvsb.ecommercewhitelabel.presentation.activity.PaymentActivity
import com.pvsb.ecommercewhitelabel.presentation.adapter.CartProductsAdapter
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentCart : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by viewModels()
    private val mAdapter = CartProductsAdapter(::deleteProduct)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialSetUp()
    }

    private fun initialSetUp() {
        binding.apply {
            rvProducts.adapter = mAdapter

            btnPurchase.setOnClickListener {
                requireContext().openActivity(PaymentActivity::class.java)
            }
        }

        lifecycleScope.launch {
            requireContext().getValueDS(stringPreferencesKey(CART_ID)) {
                it?.let { id ->
                    viewModel.getCartContent(id)
                }
            }
        }

        setUpObservers()
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cartContent.collectLatest { state ->
                    handleResponse<PopulateCartDTO>(state,
                        onSuccess = {
                            binding.vfMain.displayedChild = DATA_STATE
                            binding.clMainContent.visibility = View.VISIBLE
                            mAdapter.submitList(it.products)
                            binding.tvCartValue.text = it.total.formatCurrency()
                        }, onError = {
                            binding.vfMain.displayedChild = EMPTY_STATE
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }, onEmpty = {
                            binding.vfMain.displayedChild = EMPTY_STATE
                        })
                }
            }
        }
    }

    private fun deleteProduct(product: CartProductsDTO) {
        lifecycleScope.launch {
            context?.getValueDS(stringPreferencesKey(CART_ID)) {
                it?.let { cartId ->
                    viewModel.deleteProduct(cartId, product)
                }
            }
        }
    }

    companion object {
        const val DATA_STATE = 0
        const val EMPTY_STATE = 1
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}