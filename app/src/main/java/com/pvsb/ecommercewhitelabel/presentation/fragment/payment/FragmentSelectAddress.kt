package com.pvsb.ecommercewhitelabel.presentation.fragment.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.pvsb.core.model.UserAddressDTO
import com.pvsb.core.utils.Constants.PrefsKeys.USER_ID
import com.pvsb.core.utils.getValueDS
import com.pvsb.core.utils.handleResponse
import com.pvsb.core.utils.onBackPress
import com.pvsb.core.utils.switchFragment
import com.pvsb.ecommercewhitelabel.databinding.FragmentSelectAddressBinding
import com.pvsb.ecommercewhitelabel.presentation.adapter.SelectAddressAdapter
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.CartViewModel
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.PaymentViewModel
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentSelectAddress : Fragment() {

    private var _binding: FragmentSelectAddressBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()
    private val hostViewModel: PaymentViewModel by activityViewModels()
    private val mAdapter = SelectAddressAdapter(::onDetailsClick)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialSetUp()
    }

    private fun initialSetUp() {
        binding.apply {
            ivBack.setOnClickListener {
                onBackPress()
            }

            btnContinue.setOnClickListener {
                val selectedAddress = mAdapter.currentList.find { it.isChecked }
                hostViewModel.selectedAddress = selectedAddress
                switchFragment(FragmentPayment(), saveBackStack = true)
            }

            rvAddresses.adapter = mAdapter
        }

        lifecycleScope.launch {
            context?.getValueDS(stringPreferencesKey(USER_ID)) {
                it?.let {
                    getAddresses(it)
                }
            }
        }
    }

    private fun getAddresses(userId: String) {
        profileViewModel.getAddresses(userId)
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                handleResponse<List<UserAddressDTO>>(state,
                    onSuccess = {
                        mAdapter.submitList(it)
                    }, onEmpty = {
                        Toast.makeText(context, "no address", Toast.LENGTH_SHORT).show()
                    }, onError = {

                    })
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onDetailsClick(address: UserAddressDTO) {

    }
}