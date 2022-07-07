package com.pvsb.ecommercewhitelabel.data.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pvsb.core.firebase.model.CreateAccountReqDTO
import com.pvsb.core.firebase.model.CreateAccountResDTO
import com.pvsb.core.firebase.model.LoginReqDTO
import com.pvsb.core.firebase.model.LoginResDTO
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    private val auth = Firebase.auth

    override suspend fun doLogin(data: LoginReqDTO): LoginResDTO {
        return suspendCoroutine { continuation ->

            val email = data.email
            val password = data.password

            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    it.user?.uid?.let { userId ->

                        val result = LoginResDTO(email, password, userId)
                        continuation.resumeWith(Result.success(result))
                    }
                }
                .addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }

    override suspend fun createAccount(data: CreateAccountReqDTO): CreateAccountResDTO {
        return suspendCoroutine { continuation ->

            val email = data.email
            val password = data.password

            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    it.user?.uid?.let { userId ->

                        val result = CreateAccountResDTO(email, password, userId)
                        continuation.resumeWith(Result.success(result))
                    }
                }
                .addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }
}