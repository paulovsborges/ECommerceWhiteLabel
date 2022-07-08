package com.pvsb.ecommercewhitelabel.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.pvsb.core.firebase.model.CreateAccountResDTO
import com.pvsb.core.firebase.model.LoginReqDTO
import com.pvsb.core.firebase.model.LoginResDTO
import com.pvsb.core.utils.*
import com.pvsb.core.utils.Constants.PrefsKeys.USER_ID
import com.pvsb.core.utils.Constants.PrefsKeys.USER_NAME
import com.pvsb.ecommercewhitelabel.databinding.FragmentProfileBinding
import com.pvsb.ecommercewhitelabel.presentation.activity.ActivityCreateAccount
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.ProfileVIewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentProfile : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileVIewModel by viewModels()
    private var createAccountListenerLauncher: ActivityResultLauncher<Intent> =
        setUpActivityListener(
            ActivityCreateAccount::class.java.simpleName, ::doLoginAfterAccountCreation
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

        initialSetUp()
        setUpObservers()
    }

    private fun initialSetUp() {
        binding.apply {
            lifecycleScope.launch {
                requireContext().getValueDS(stringPreferencesKey(USER_ID)) {
                    vfMain.displayedChild = if (it.isNullOrEmpty()) {
                        initialLoginSetup()
                        LOGIN_LAYOUT
                    } else {
                        initialProfileSetup()
                        PROFILE_LAYOUT
                    }
                    root.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initialProfileSetup() {
        binding.iclProfileLayout.apply {
            btnLogout.setOnClickListener {
                doLogout()
            }
        }
    }

    private fun initialLoginSetup() {
        binding.iclLoginLayout.apply {

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

        binding.iclLoginLayout.apply {
            val email = tiEmail.editText?.text.toString()
            val password = tiPassword.editText?.text.toString()
            val req = LoginReqDTO(email, password)
            viewModel.doLogin(req)
        }
    }

    private fun doLoginAfterAccountCreation(data: CreateAccountResDTO) {

        binding.iclLoginLayout.apply {
            tiEmail.editText?.text?.clear()
            tiPassword.editText?.text?.clear()
        }

        val req = LoginReqDTO(
            data.email, data.password
        )

        viewModel.doLogin(req)
    }

    private fun doLogout() {
        lifecycleScope.launch {
            requireContext().removeValueDS(stringPreferencesKey(USER_ID))
            requireContext().removeValueDS(stringPreferencesKey(USER_NAME))
        }

        binding.vfMain.displayedChild = LOGIN_LAYOUT
    }

    private fun setUpObservers() {

        viewModel.doLogin
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                handleResponse<LoginResDTO>(state,
                    onSuccess = {

                        requireContext().putValueDS(stringPreferencesKey(USER_ID), it.userId)

                        binding.vfMain.displayedChild = PROFILE_LAYOUT

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