package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pvsb.core.utils.switchFragment
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.databinding.ActivityPaymentBinding
import com.pvsb.ecommercewhitelabel.presentation.fragment.payment.FragmentSelectAddress

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialSetup()
    }

    private fun initialSetup() {
        switchFragment(
            fragment = FragmentSelectAddress(),
            container = R.id.fcvPayment,
            stackName = supportFragmentManager.toString()
        )
    }
}