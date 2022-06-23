package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationBarView
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.databinding.ActivityMainBinding
import com.pvsb.ecommercewhitelabel.presentation.fragment.*
import com.pvsb.ecommercewhitelabel.utils.switchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpBottomNav()
    }

    private fun setUpBottomNav() {
        val navBottomListener = NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navHome -> {
                    switchFragment(FragmentHome(), R.id.fcvMain)
                    true
                }
                R.id.navSearch -> {
                    switchFragment(FragmentSearch(), R.id.fcvMain)
                    true
                }
                R.id.navCart -> {
                    switchFragment(FragmentCart(), R.id.fcvMain)
                    true
                }
                else -> {
                    switchFragment(FragmentProfile(), R.id.fcvMain)
                    true
                }
            }
        }

        binding.mainBottomNav.apply {
            setOnItemSelectedListener(navBottomListener)
            selectedItemId = R.id.navHome
        }
    }
}