package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.pvsb.core.utils.*
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.databinding.ActivityMainBinding
import com.pvsb.ecommercewhitelabel.presentation.fragment.*
import com.pvsb.ecommercewhitelabel.presentation.fragment.FragmentCart
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

    private fun test() {

        lifecycleScope.launch {
            putValueDS(keyName = stringPreferencesKey("key"), value = "value")

            getValueDS(keyName = stringPreferencesKey("key")) {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            }

            removeValueDS(keyName = stringPreferencesKey("key"))

            clearDS()
        }
    }
}