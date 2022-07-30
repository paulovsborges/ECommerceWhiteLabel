package com.pvsb.ecommercewhitelabel.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.pvsb.core.model.HomeBanner
import com.pvsb.core.model.ProductDTO
import com.pvsb.core.utils.Constants.PRODUCT_DETAILS
import com.pvsb.core.utils.handleResponse
import com.pvsb.core.utils.openActivity
import com.pvsb.core.utils.putValueOnBundle
import com.pvsb.ecommercewhitelabel.databinding.FragmentHomeBinding
import com.pvsb.ecommercewhitelabel.presentation.activity.ActivityProductDetails
import com.pvsb.ecommercewhitelabel.presentation.adapter.HomeAdapter
import com.pvsb.ecommercewhitelabel.presentation.adapter.HomeBannersAdapter
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FragmentHome : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mAdapter = HomeAdapter(::navigateToDetails)
    private val viewModel: HomeViewModel by activityViewModels()
    private val bannersAdapter = HomeBannersAdapter(::onBannerClick)

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
        binding.apply {
            rvMain.adapter = mAdapter
            rvBanners.adapter = bannersAdapter
        }
        getHomeData()
    }

    private fun getHomeData() {
        viewModel.getHomeData()
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                handleResponse<List<ProductDTO>>(
                    state, onSuccess = {
                        setBannersData(it)
                        mAdapter.submitList(it)
                        binding.clMainContent.visibility = View.VISIBLE
                    }, onError = {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                )
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setBannersData(products: List<ProductDTO>) {
        val banners = mutableListOf<HomeBanner>()
        val firstCategory = products.filter { it.categoryId == 1 }.take(2)
        val secondCategory = products.filter { it.categoryId == 2 }.take(2)
        val thirdCategory = products.filter { it.categoryId == 3 }.take(2)

        banners.add(
            HomeBanner(
                "Hardware",
                1,
                firstCategory.first().imageUrl,
                firstCategory[1].imageUrl
            )
        )
        banners.add(
            HomeBanner(
                "Peripherals",
                2,
                secondCategory.first().imageUrl,
                secondCategory[1].imageUrl
            )
        )
        banners.add(
            HomeBanner(
                "Cases",
                3,
                thirdCategory.first().imageUrl,
                thirdCategory[1].imageUrl
            )
        )

        bannersAdapter.submitList(banners)
    }

    private fun navigateToDetails(item: ProductDTO) {

        requireContext().openActivity(ActivityProductDetails::class.java) {
            it.putValueOnBundle(PRODUCT_DETAILS, item)
        }
    }

    private fun onBannerClick(id: Int) {

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}