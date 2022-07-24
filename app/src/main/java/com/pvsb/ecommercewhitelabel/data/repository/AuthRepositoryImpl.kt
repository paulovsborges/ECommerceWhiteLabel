package com.pvsb.ecommercewhitelabel.data.repository

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pvsb.core.model.*
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    private val auth = Firebase.auth

    override suspend fun doLogin(data: LoginReqDTO): UserPersonalData {
        return suspendCoroutine { continuation ->

            val email = data.email
            val password = data.password

            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    it.user?.uid?.let { userId ->
                        Firebase.firestore.collection("users")
                            .document(userId)
                            .get().addOnSuccessListener { document ->
                                document.toObject(CreateUserRegistrationReqDTO::class.java)
                                    ?.let { userData ->
                                        continuation.resumeWith(Result.success(userData.personalData))
                                    } ?: kotlin.run {
                                    continuation.resumeWith(Result.failure(Exception("No user data")))
                                }
                            }
                            .addOnFailureListener { e ->
                                continuation.resumeWith(Result.failure(e))
                            }
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

    override suspend fun createUserCollection(data: CreateUserRegistrationReqDTO): Boolean {
        val db = Firebase.firestore

        return suspendCoroutine { continuation ->
            db.collection("users")
                .document(data.personalData.userId)
                .set(data)
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(true))
                }
                .addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String) {
        return suspendCoroutine { continuation ->
            auth.currentUser?.let { user ->
                user.email?.let { email ->
                    val credential = EmailAuthProvider.getCredential(email, oldPassword)
                    user.reauthenticate(credential)
                        .addOnSuccessListener {
                            user.updatePassword(newPassword)
                                .addOnSuccessListener {
                                    continuation.resumeWith(Result.success(Unit))
                                }
                                .addOnFailureListener {
                                    continuation.resumeWith(Result.failure(it))
                                }
                        }
                        .addOnFailureListener {
                            continuation.resumeWith(Result.failure(it))
                        }
                }
            }
        }
    }
}