package com.pvsb.ecommercewhitelabel.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.databinding.FragmentMainBinding
import com.pvsb.ecommercewhitelabel.utils.switchFragment

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}