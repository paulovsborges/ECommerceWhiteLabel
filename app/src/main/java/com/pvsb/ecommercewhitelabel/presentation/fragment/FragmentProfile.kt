package com.pvsb.ecommercewhitelabel.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.pvsb.core.firebase.model.CreateAccountResDTO
import com.pvsb.core.firebase.model.LoginReqDTO
import com.pvsb.core.firebase.model.LoginResDTO
import com.pvsb.core.utils.handleResponse
import com.pvsb.core.utils.setUpActivityListener
import com.pvsb.ecommercewhitelabel.databinding.FragmentProfileBinding
import com.pvsb.ecommercewhitelabel.presentation.activity.ActivityCreateAccount
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.ProfileVIewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FragmentProfile : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileVIewModel by viewModels()
    private var createAccountListenerLauncher: ActivityResultLauncher<Intent> =
        setUpActivityListener(
            ActivityCreateAccount::class.java.simpleName, ::doLoginAfterCreateAccount
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vfMain.displayedChild = LOGIN_LAYOUT
        setUpLoginScreen()
        setUpObservers()
    }

    private fun setUpLoginScreen() {
        binding.apply {

            btnLogin.setOnClickListener {
                doLogin()
            }

            btnCreateAccount.setOnClickListener {
                createAccountListenerLauncher.launch(
                    Intent(
                        requireContext(),
                        ActivityCreateAccount::class.java
                    )
                )
            }
        }
    }

    private fun doLogin() {

        binding.apply {
            val email = tiEmail.editText?.text.toString()
            val password = tiPassword.editText?.text.toString()
            val req = LoginReqDTO(email, password)
            viewModel.doLogin(req)
        }
    }

    private fun doLoginAfterCreateAccount(data: CreateAccountResDTO) {

        binding.apply {
            tiEmail.editText?.text?.clear()
            tiPassword.editText?.text?.clear()
        }

        val req = LoginReqDTO(
            data.email, data.password
        )

        viewModel.doLogin(req)
    }

    private fun setUpObservers() {

        viewModel.doLogin
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                handleResponse<LoginResDTO>(state,
                    onSuccess = {
                        Toast.makeText(requireContext(), it.userId, Toast.LENGTH_SHORT).show()
                    },
                    onError = {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    })
            }
            .launchIn(lifecycleScope)
    }

    private companion object {
        const val LOGIN_LAYOUT = 0
        const val PROFILE_LAYOUT = 1
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}