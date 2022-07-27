package com.pvsb.ecommercewhitelabel.presentation.fragment.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.pvsb.core.model.UserAddressDTO
import com.pvsb.core.utils.*
import com.pvsb.core.utils.Constants.PrefsKeys.USER_ID
import com.pvsb.ecommercewhitelabel.databinding.FragmentAddressesListBinding
import com.pvsb.ecommercewhitelabel.presentation.adapter.AddressesAdapter
import com.pvsb.ecommercewhitelabel.presentation.fragment.orders.FragmentOrderDetails
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentAddressesList : Fragment() {

    private var _binding: FragmentAddressesListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private val mAdapter = AddressesAdapter(::deleteAddress, ::seeAddressDetails)

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
        initialSetUp()
    }

    private fun initialSetUp() {
        lifecycleScope.launch {
            context?.getValueDS(stringPreferencesKey(USER_ID)) {
                it?.let {
                    getAddresses(it)
                }
            }
        }

        binding.apply {
            rvAddresses.adapter = mAdapter

            ivBack.setOnClickListener {
                onBackPress()
            }

            btnNewAddress.setOnClickListener {
                switchFragment(FragmentCreateAddress(), saveBackStack = true)
            }
        }
    }

    private fun getAddresses(userId: String) {
        viewModel.getAddresses(userId)
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                handleResponse<List<UserAddressDTO>>(state,
                    onSuccess = {
                        mAdapter.submitList(it)
                    },
                    onEmpty = {
                        switchFragment(FragmentCreateAddress())
                    }, onError = {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    })
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun seeAddressDetails(address: UserAddressDTO) {
        switchFragmentWithArgs(
            FragmentCreateAddress(),
            saveBackStack = true,
            data = address
        )
    }

    private fun deleteAddress(address: UserAddressDTO) {
        lifecycleScope.launch {
            context?.getValueDS(stringPreferencesKey(USER_ID)) {
                it?.let { userId ->
                    viewModel.deleteAddress(userId, address)
                        .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                        .onEach { state ->
                            handleResponse<Boolean>(state,
                                onSuccess = {
                                    getAddresses(userId)
                                }, onError = {
                                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                })
                        }
                        .launchIn(viewLifecycleOwner.lifecycleScope)
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}