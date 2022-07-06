package com.pvsb.ecommercewhitelabel.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pvsb.core.firestore.model.ProductDTO
import com.pvsb.core.utils.*
import com.pvsb.core.utils.Constants.PRODUCT_NAME
import com.pvsb.ecommercewhitelabel.databinding.FragmentHomeBinding
import com.pvsb.ecommercewhitelabel.presentation.activity.ActivityProductDetails
import com.pvsb.ecommercewhitelabel.presentation.adapter.HomeAdapter
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentHome : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mAdapter = HomeAdapter(::navigateToDetails)
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialSetUp()
    }

    private fun initialSetUp() {
        binding.rvMain.adapter = mAdapter
        viewModel.getHomeData()
        setObservers()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeContent.collectLatest { state ->
                    state.handleResponseState<List<ProductDTO>>(
                        fragment = this@FragmentHome,
                        onSuccess = {
                            mAdapter.submitList(it)
                        }, onError = {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }, onEmpty = {

                        })

//                    when (state) {
//                        is ResponseState.Init -> {}
//                        is ResponseState.Loading -> {
//                            showLoading()
//                        }
//                        is ResponseState.Complete.Success<*> -> {
//                            (state.data as? ResponseState.Complete.Success<List<ProductDTO>>)?.also { result ->
//                                mAdapter.submitList(result.data)
//                            }
//                            hideLoading()
//                        }
//                        is ResponseState.Complete.Empty -> {}
//                        is ResponseState.Complete.Fail -> {
//                            Toast.makeText(requireContext(), state.exception.message, Toast.LENGTH_SHORT).show()
//                            hideLoading()
//                        }
//                    }
                }
            }
        }
    }

    private fun navigateToDetails(item: ProductDTO) {
        requireContext().openActivity(ActivityProductDetails::class.java) {
            it.putExtra(PRODUCT_NAME, item)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}