package com.pvsb.ecommercewhitelabel.presentation.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pvsb.core.firebase.model.CreateAccountResDTO
import com.pvsb.core.utils.setResultAndFinish
import com.pvsb.ecommercewhitelabel.databinding.ActivityCreateAccountBinding

class ActivityCreateAccount : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreateAccount.setOnClickListener {
            createAccountAndFinish()
        }
    }

    private fun createAccountAndFinish() {
        binding.apply {
            val email = tiEmail.editText?.text.toString()
            val password = tiPassword.editText?.text.toString()

            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    it.user?.uid?.let { userId ->
                        val obj = CreateAccountResDTO(
                            email = email,
                            password = password,
                            userId = userId
                        )
                        setResultAndFinish(obj)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this@ActivityCreateAccount, it.message, Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }
}