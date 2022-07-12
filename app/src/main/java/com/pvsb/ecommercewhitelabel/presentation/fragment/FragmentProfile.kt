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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pvsb.core.firebase.model.CreateAccountResDTO
import com.pvsb.core.firebase.model.LoginReqDTO
import com.pvsb.core.firebase.model.LoginResDTO
import com.pvsb.core.firebase.model.UserPersonalData
import com.pvsb.core.utils.*
import com.pvsb.core.utils.Constants.PrefsKeys.USER_ID
import com.pvsb.core.utils.Constants.PrefsKeys.USER_NAME
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.databinding.FragmentProfileBinding
import com.pvsb.ecommercewhitelabel.presentation.activity.ActivityCreateAccount
import com.pvsb.ecommercewhitelabel.presentation.activity.ActivityUserRegistration
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentProfile : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()
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

        setUpObservers()
        initialSetUp()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initialSetUp() {
        binding.apply {
            lifecycleScope.launch {
                requireContext().getValueDS(stringPreferencesKey(USER_ID)) {
                    if (it.isNullOrEmpty()) {
                        initialLoginSetup()
                    } else {
                        initialProfileSetup()
                    }
                    root.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initialProfileSetup() {
        binding.vfMain.displayedChild = PROFILE_LAYOUT
        binding.iclProfileLayout.apply {
            btnLogout.setOnClickListener {
                doLogout()
            }

            btnRegistration.setOnClickListener {
                requireContext().openActivity(ActivityUserRegistration::class.java)
            }

            lifecycleScope.launch {
                requireContext().getValueDS(stringPreferencesKey(USER_NAME)) {
                    it?.let {

                        val name = it.replaceFirstChar { fChar ->
                            fChar.uppercase()
                        }

                        tvUserName.text = getString(R.string.profile_user_greetings, name)
                    }
                }
            }
        }
    }

    private fun initialLoginSetup() {
        binding.vfMain.displayedChild = LOGIN_LAYOUT
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
        initialLoginSetup()
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.doLogin
                    .collect { state ->
                        handleResponse<UserPersonalData>(state,
                            onSuccess = {

                                binding.iclLoginLayout.apply {
                                    tiEmail.editText?.text?.clear()
                                    tiPassword.editText?.text?.clear()
                                }

                                requireContext().putValueDS(
                                    stringPreferencesKey(USER_ID),
                                    it.userId
                                )

                                requireContext().putValueDS(
                                    stringPreferencesKey(USER_NAME),
                                    it.name
                                )

                                initialProfileSetup()
                            },
                            onError = {
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                                    .show()
                            })
                    }
            }
        }
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