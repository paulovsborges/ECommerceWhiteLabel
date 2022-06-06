package com.pvsb.ecommercewhitelabel.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.data.model.MainHomeModel
import com.pvsb.ecommercewhitelabel.databinding.FragmentHomeBinding
import com.pvsb.ecommercewhitelabel.presentation.adapter.HomeAdapter
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.HomeVIewModel
import com.pvsb.ecommercewhitelabel.utils.switchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentHome : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mAdapter = HomeAdapter(::navigateToDetails)
    private val viewModel: HomeVIewModel by viewModels()

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
        viewModel.homeData.observe(viewLifecycleOwner) {
            mAdapter.submitList(it)
        }
    }

    private fun navigateToDetails(item: MainHomeModel) {
        requireActivity().switchFragment(
            FragmentProductDetails(),
            R.id.mainContainer,
            bundleOf("itemTest" to item)
        )
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}