package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationBarView
import com.pvsb.core.utils.createBottomNavListener
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.databinding.ActivityMainBinding
import com.pvsb.ecommercewhitelabel.presentation.fragment.*
import com.pvsb.core.utils.switchFragment
import com.pvsb.ecommercewhitelabel.presentation.fragment.FragmentCart
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
        val navBottomListener = createBottomNavListener(
            R.id.fcvMain, mapOf(
                R.id.navHome to FragmentHome(),
                R.id.navSearch to FragmentSearch(),
                R.id.navCart to FragmentCart(),
                R.id.navProfile to FragmentProfile()
            )
        )

        binding.mainBottomNav.apply {
            setOnItemSelectedListener(navBottomListener)
            selectedItemId = R.id.navHome
        }
    }
}