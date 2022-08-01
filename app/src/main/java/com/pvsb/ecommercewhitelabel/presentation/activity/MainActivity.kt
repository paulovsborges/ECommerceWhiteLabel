package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.pvsb.core.utils.Constants.Navigator.BOTTOM_NAV_CART
import com.pvsb.core.utils.Constants.Navigator.BOTTOM_NAV_HOME
import com.pvsb.core.utils.Constants.Navigator.BOTTOM_NAV_PROFILE
import com.pvsb.core.utils.Constants.Navigator.BOTTOM_NAV_SEARCH
import com.pvsb.core.utils.createBottomNavListener
import com.pvsb.ecommercewhitelabel.R
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
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpBottomNav()
        navigateByAction()
    }

    private fun setUpBottomNav() {
        val navBottomListener = createBottomNavListener(
            R.id.fcvMain,
            mapOf(
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

    private fun navigateByAction() {

        val action = intent?.action

        val destinations = mapOf(
            BOTTOM_NAV_CART to R.id.navCart,
            BOTTOM_NAV_SEARCH to R.id.navSearch,
            BOTTOM_NAV_PROFILE to R.id.navProfile,
            BOTTOM_NAV_HOME to R.id.navHome,
        )

        action?.let {
            destinations[it].let { menu ->
                binding.mainBottomNav.selectedItemId = menu ?: R.id.navHome
            }
        }
    }
}
