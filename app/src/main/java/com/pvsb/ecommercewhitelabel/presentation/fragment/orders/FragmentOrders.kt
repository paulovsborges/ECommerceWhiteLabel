package com.pvsb.ecommercewhitelabel.presentation.fragment.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.pvsb.core.model.OderModelReqDTO
import com.pvsb.core.model.OderModelResDTO
import com.pvsb.core.model.enums.OrderSituationEnum
import com.pvsb.core.utils.Constants.PrefsKeys.USER_ID
import com.pvsb.core.utils.getValueDS
import com.pvsb.core.utils.handleResponse
import com.pvsb.core.utils.onBackPress
import com.pvsb.core.utils.switchFragment
import com.pvsb.ecommercewhitelabel.databinding.FragmentOrdersBinding
import com.pvsb.ecommercewhitelabel.databinding.FragmentOrdersListBinding
import com.pvsb.ecommercewhitelabel.presentation.adapter.OrdersViewPagerAdapter
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentOrders : Fragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialSetUp()
    }

    private fun initialSetUp() {
        binding.ivBack.setOnClickListener {
            onBackPress()
        }
        lifecycleScope.launch {
            context?.getValueDS(stringPreferencesKey(USER_ID)) {
                it?.let {
                    viewModel.getOrders(it)
                }
            }
        }
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.orders
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                handleResponse<OderModelResDTO>(state,
                    onSuccess = {
                        setUpViewPager(it)
                    }, onError = {

                    })
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setUpViewPager(data: OderModelResDTO) {
        binding.apply {
            vpOrders.adapter = OrdersViewPagerAdapter(requireActivity(), data, ::onOrderClicked)
            TabLayoutMediator(tlOrdersType, vpOrders) { tab, pos ->
                when (pos) {
                    0 -> {
                        tab.text = getString(OrderSituationEnum.PAYMENT.label)
                    }
                    1 -> {
                        tab.text = getString(OrderSituationEnum.IN_TRANSPORT.label)
                    }
                    2 -> {
                        tab.text = getString(OrderSituationEnum.DELIVERY_ROUTE.label)
                    }
                    3 -> {
                        tab.text = getString(OrderSituationEnum.CONCLUDED.label)
                    }
                }
            }.attach()
        }
    }

    private fun onOrderClicked(item: OderModelReqDTO) {
        switchFragment(
            FragmentOrderDetails(),
            saveBackStack = true,
            data = bundleOf("ITEM_DETAILS" to item)
        )
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}