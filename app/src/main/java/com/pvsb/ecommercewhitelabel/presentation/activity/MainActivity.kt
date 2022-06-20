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
        initialSetup()

    }

    private fun initialSetup() {
        supportFragmentManager.beginTransaction().apply {

            add(R.id.mainContainer, MainFragment(), "main nav host")

//            replace(R.id.mainContainer, MainFragment(), "main nav host")
            commit()
        }
    }

    override fun onBackPressed() {

        val fragmentsCount =
            supportFragmentManager.findFragmentById(R.id.mainContainer)?.parentFragmentManager?.fragments?.size

        fragmentsCount?.let {
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        }

//        super.onBackPressed()

    }
}