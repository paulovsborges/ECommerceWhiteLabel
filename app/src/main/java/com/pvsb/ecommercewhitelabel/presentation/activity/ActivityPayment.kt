package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.pvsb.core.utils.Constants.PrefsKeys.CART_ID
import com.pvsb.core.utils.getValueDS
import com.pvsb.core.utils.switchFragment
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.databinding.ActivityPaymentBinding
import com.pvsb.ecommercewhitelabel.presentation.fragment.payment.FragmentSelectAddress
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityPayment : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    private val viewModel: PaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialSetup()
    }

    private fun initialSetup() {

        lifecycleScope.launch {
            getValueDS(stringPreferencesKey(CART_ID)) {
                it?.let {
                    viewModel.getCartContent(it)
                }
            }
        }

        switchFragment(
            fragment = FragmentSelectAddress(),
            container = R.id.fcvPayment,
            stackName = supportFragmentManager.toString()
        )
    }
}
