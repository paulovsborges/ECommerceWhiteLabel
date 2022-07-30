package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pvsb.core.utils.switchFragment
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.databinding.ActivityOrdersBinding
import com.pvsb.ecommercewhitelabel.presentation.fragment.orders.FragmentOrders
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityOrders : AppCompatActivity() {

    private lateinit var binding: ActivityOrdersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialSetUp()
    }

    private fun initialSetUp() {
        binding.apply {
            switchFragment(FragmentOrders(), R.id.fcvOrders)
        }
    }
}
