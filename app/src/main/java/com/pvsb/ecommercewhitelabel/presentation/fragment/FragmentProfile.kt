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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pvsb.core.model.CreateAccountResDTO
import com.pvsb.core.model.LoginReqDTO
import com.pvsb.core.model.UserPersonalData
import com.pvsb.core.utils.*
import com.pvsb.core.utils.Constants.PrefsKeys.USER_ID
import com.pvsb.core.utils.Constants.PrefsKeys.USER_NAME
import com.pvsb.ecommercewhitelabel.R
import com.pvsb.ecommercewhitelabel.databinding.FragmentProfileBinding
import com.pvsb.ecommercewhitelabel.presentation.activity.*
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentProfile : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by activityViewModels()
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
    }

    private fun initialSetUp() {
        binding.apply {
            lifecycleScope.launch {
                requireContext().getValueDS(stringPreferencesKey(USER_ID)) {
                    if (it.isNullOrEmpty()) {
                        loginSetUp()
                    } else {
                        profileSetUp()
                    }
                    root.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun profileSetUp() {
        binding.vfMain.displayedChild = PROFILE_LAYOUT
        binding.iclProfileLayout.apply {
            btnLogout.setOnClickListener { doLogout() }

            btnRegistration.setOnClickListener {
                context?.openActivity(ActivityUserRegistration::class.java)
            }

            btnFavorites.setOnClickListener {
                context?.openActivity(ActivityUserFavoritesProducts::class.java)
            }

            btnAddresses.setOnClickListener {
                context?.openActivity(ActivityAddresses::class.java)
            }

            btnOrders.setOnClickListener {
                context?.openActivity(ActivityOrders::class.java)
            }

            btnChangePassword.setOnClickListener {
                context?.openActivity(ActivityChangePassword::class.java)
            }

            lifecycleScope.launch {
                context?.getValueDS(stringPreferencesKey(USER_NAME)) {
                    it?.let {
                        val name = it.getUserFirstName().replaceFirstChar { fChar ->
                            fChar.uppercase()
                        }
                        tvUserName.text = getString(R.string.profile_user_greetings, name)
                    }
                }
            }
        }
    }

    private fun loginSetUp() {
        binding.vfMain.displayedChild = LOGIN_LAYOUT
        binding.iclLoginLayout.apply {
            btnLogin.setOnClickListener { doLogin() }
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
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .onEach { state ->
                    handleResponse<UserPersonalData>(state,
                        onSuccess = {
                            onLoginSuccessful(it)
                        },
                        onError = {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                                .show()
                        })
                }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private fun doLoginAfterAccountCreation(data: CreateAccountResDTO) {
        val req = LoginReqDTO(data.email, data.password)
        viewModel.doLogin(req)
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                handleResponse<UserPersonalData>(state,
                    onSuccess = {
                        onLoginSuccessful(it)
                    },
                    onError = {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                            .show()
                    })
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private suspend fun onLoginSuccessful(data: UserPersonalData) {

        binding.iclLoginLayout.apply {
            tiEmail.editText?.text?.clear()
            tiPassword.editText?.text?.clear()
        }

        context?.apply {
            putValueDS(stringPreferencesKey(USER_ID), data.userId)
            putValueDS(stringPreferencesKey(USER_NAME), data.name)
        }
        profileSetUp()
    }

    private fun doLogout() {
        lifecycleScope.launch {
            context?.apply {
                removeValueDS(stringPreferencesKey(USER_ID))
                removeValueDS(stringPreferencesKey(USER_NAME))
            }
            Firebase.auth.signOut()
        }
        loginSetUp()
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