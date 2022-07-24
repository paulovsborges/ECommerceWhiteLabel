package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.pvsb.core.model.UserAddressDTO
import com.pvsb.core.utils.Constants
import com.pvsb.core.utils.getValueDS
import com.pvsb.core.utils.handleResponse
import com.pvsb.core.utils.switchFragment
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.databinding.ActivityAddressesBinding
import com.pvsb.ecommercewhitelabel.presentation.fragment.address.FragmentAddressesList
import com.pvsb.ecommercewhitelabel.presentation.fragment.address.FragmentCreateAddress
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityAddresses : AppCompatActivity() {

    private lateinit var binding: ActivityAddressesBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialSetUp()
    }

    private fun initialSetUp() {
        lifecycleScope.launch {
            getValueDS(stringPreferencesKey(Constants.PrefsKeys.USER_ID)) {
                it?.let {
                    getAddresses(it)
                }
            }
        }
    }

    private fun getAddresses(userId: String) {
        viewModel.getAddresses(userId)
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                handleResponse<List<UserAddressDTO>>(state,
                    onSuccess = {
                        switchFragment(FragmentAddressesList(), R.id.fcvAddressContainer)
                    },
                    onEmpty = {
                        switchFragment(FragmentCreateAddress(), R.id.fcvAddressContainer)
                    }, onError = {

                    })

            }
            .launchIn(lifecycleScope)
    }
}