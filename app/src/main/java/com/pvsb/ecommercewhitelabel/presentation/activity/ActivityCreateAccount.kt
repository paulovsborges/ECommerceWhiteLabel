package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.pvsb.core.firebase.model.CreateAccountReqDTO
import com.pvsb.core.firebase.model.CreateAccountResDTO
import com.pvsb.core.utils.handleResponse
import com.pvsb.core.utils.setResultAndFinish
import com.pvsb.ecommercewhitelabel.databinding.ActivityCreateAccountBinding
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.ProfileVIewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ActivityCreateAccount : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding
    private val viewModel: ProfileVIewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpObservers()
        binding.btnCreateAccount.setOnClickListener {
            createAccountAndFinish()
        }
    }

    private fun createAccountAndFinish() {
        binding.apply {
            val email = tiEmail.editText?.text.toString()
            val password = tiPassword.editText?.text.toString()

            val req = CreateAccountReqDTO(
                email, password
            )

            viewModel.createAccount(req)
        }
    }

    private fun setUpObservers() {
        viewModel.createAccount
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                handleResponse<CreateAccountResDTO>(state, onSuccess = {

                    val obj = CreateAccountResDTO(
                        email = it.email,
                        password = it.password,
                        userId = it.userId
                    )

                    setResultAndFinish(obj)

                }, onError = {
                    Toast.makeText(this@ActivityCreateAccount, it.message, Toast.LENGTH_SHORT)
                        .show()
                })
            }
            .launchIn(lifecycleScope)
    }
}