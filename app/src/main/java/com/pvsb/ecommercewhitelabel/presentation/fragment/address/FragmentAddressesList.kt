package com.pvsb.ecommercewhitelabel.presentation.fragment.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.pvsb.core.model.UserAddressDTO
import com.pvsb.core.utils.Constants.PrefsKeys.USER_ID
import com.pvsb.core.utils.getValueDS
import com.pvsb.core.utils.handleResponse
import com.pvsb.core.utils.popBackStack
import com.pvsb.core.utils.switchFragment
import com.pvsb.ecommercewhitelabel.databinding.FragmentAddressesListBinding
import com.pvsb.ecommercewhitelabel.presentation.adapter.AddressesAdapter
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
                    viewModel.getAddresses(it)
                }
            }
        }

        setUpObservers()

        binding.apply {
            rvAddresses.adapter = mAdapter

            ivBack.setOnClickListener {
                popBackStack()
            }

            btnNewAddress.setOnClickListener {
                switchFragment(FragmentCreateAddress(), saveBackStack = true)
            }
        }
    }

    private fun setUpObservers() {
        viewModel.addresses
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                handleResponse<List<UserAddressDTO>>(state,
                    onSuccess = {
                        mAdapter.submitList(it)
                    }, onError = {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    })
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun seeAddressDetails(address: UserAddressDTO) {

    }

    private fun deleteAddress(address: UserAddressDTO) {

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}