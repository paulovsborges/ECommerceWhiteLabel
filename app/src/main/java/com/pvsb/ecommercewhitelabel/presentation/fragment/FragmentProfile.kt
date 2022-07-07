package com.pvsb.ecommercewhitelabel.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pvsb.core.firebase.model.CreateAccountResDTO
import com.pvsb.core.utils.setUpActivityListener
import com.pvsb.ecommercewhitelabel.databinding.FragmentProfileBinding
import com.pvsb.ecommercewhitelabel.presentation.activity.ActivityCreateAccount

class FragmentProfile : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private var resultLauncher: ActivityResultLauncher<Intent> = setUpActivityListener(
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

        auth = Firebase.auth
        binding.vfMain.displayedChild = LOGIN_LAYOUT
        setUpLoginScreen()
    }

    private fun setUpLoginScreen() {
        binding.apply {

            btnLogin.setOnClickListener {
                doLogin()
            }

            btnCreateAccount.setOnClickListener {
                resultLauncher.launch(Intent(requireContext(), ActivityCreateAccount::class.java))
            }
        }
    }

    private fun doLogin() {

        binding.apply {
            val email = tiEmail.editText?.text.toString()
            val password = tiPassword.editText?.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), it.user?.uid, Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun doLoginAfterCreateAccount(data: CreateAccountResDTO) {
        auth.signInWithEmailAndPassword(data.email, data.password)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "USER LOGGED ${data.userId}", Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
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