package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.pvsb.ecommercewhitelabel.databinding.ActivityChangePasswordBinding
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityChangePassword : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialSetUp()
    }

    private fun initialSetUp() {
        binding.apply {
            ivBack.setOnClickListener {
                finish()
            }
        }
    }
}