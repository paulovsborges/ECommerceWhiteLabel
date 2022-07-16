package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pvsb.core.utils.switchFragment
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.databinding.ActivityAddressesBinding
import com.pvsb.ecommercewhitelabel.presentation.fragment.address.FragmentCreateAddress
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityAddresses : AppCompatActivity() {

    private lateinit var binding: ActivityAddressesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialSetUp()
    }

    private fun initialSetUp() {
        switchFragment(FragmentCreateAddress(), R.id.fcvAddressContainer)
    }
}