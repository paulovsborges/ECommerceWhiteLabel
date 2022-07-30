package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.pvsb.core.utils.handleResponse
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.databinding.ActivityChangePasswordBinding
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
            btnChangePassword.setOnClickListener {
                validatePasswords()
            }
        }
    }

    private fun validatePasswords() {
        binding.apply {
            val currentPassword = tiCurrentPassword.editText?.text.toString()
            val newPassword = tiNewPassword.editText?.text.toString()
            val newPasswordConfirm = tiNewPasswordConfirmation.editText?.text.toString()

            if (currentPassword.isEmpty() || newPassword.isEmpty() || newPasswordConfirm.isEmpty()) {
                Snackbar.make(
                    binding.tvChangePasswordLabel,
                    getString(R.string.change_password_error_passwords_fields_mandatory),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                if (newPassword == newPasswordConfirm) {
                    changePassword(currentPassword, newPassword)
                } else {
                    Snackbar.make(
                        binding.tiNewPasswordConfirmation,
                        getString(R.string.change_password_error_new_and_confirm_password_do_not_match),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun changePassword(currentPassword: String, newPassword: String) {
        viewModel.changePassword(currentPassword, newPassword)
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                handleResponse<Unit>(
                    state,
                    onSuccess = {
                        finish()
                    }, onError = {
                }
                )
            }
            .launchIn(lifecycleScope)
    }
}
