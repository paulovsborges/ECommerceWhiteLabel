package com.pvsb.ecommercewhitelabel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationBarView
import com.pvsb.ecommercewhitelabel.databinding.ActivityMainBinding
import com.pvsb.ecommercewhitelabel.presentation.fragment.FragmentCart
import com.pvsb.ecommercewhitelabel.presentation.fragment.FragmentHome
import com.pvsb.ecommercewhitelabel.presentation.fragment.FragmentProfile
import com.pvsb.ecommercewhitelabel.presentation.fragment.FragmentSearch
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
                    switchFragment(FragmentHome(), R.id.mainContainer)
                    true
                }
                R.id.navSearch -> {
                    switchFragment(FragmentSearch(), R.id.mainContainer)
                    true
                }
                R.id.navCart -> {
                    switchFragment(FragmentCart(), R.id.mainContainer)
                    true
                }
                else -> {
                    switchFragment(FragmentProfile(), R.id.mainContainer)
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