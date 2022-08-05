package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pvsb.core.model.UserPersonalData
import com.pvsb.core.utils.Constants.PrefsKeys.USER_ID
import com.pvsb.core.utils.getValueDS
import com.pvsb.core.utils.handleResponse
import com.pvsb.core.utils.setSharedViewAnim
import com.pvsb.ecommercewhitelabel.databinding.ActivityUserRegistrationBinding
import com.pvsb.ecommercewhitelabel.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityUserRegistration : AppCompatActivity() {

    private lateinit var binding: ActivityUserRegistrationBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setSharedViewAnim("sharedElementOnRightSide")
        super.onCreate(savedInstanceState)
        binding = ActivityUserRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialSetUp()
    }

    private fun initialSetUp() {
        binding.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }
        }

        lifecycleScope.launch {
            getValueDS(stringPreferencesKey(USER_ID)) {
                it?.let {
                    getUserRegistration(it)
                } ?: kotlin.run {
                    Toast.makeText(this@ActivityUserRegistration, "Error", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun getUserRegistration(userId: String) {
        viewModel.getUserRegistration(userId)
            .flowWithLifecycle(lifecycle)
            .onEach { state ->
                handleResponse<UserPersonalData>(
                    state,
                    onSuccess = {
                        binding.apply {
                            Firebase.auth.currentUser?.email?.let { email ->
                                tiEmail.editText?.setText(email)
                            }
                            tiName.editText?.setText(it.name)
                            tiBirthDate.editText?.setText(it.birth)
                        }
                    }, onError = {
                    Toast.makeText(
                        this@ActivityUserRegistration,
                        it.message,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                )
            }.launchIn(lifecycleScope)
    }
}
