package com.pvsb.ecommercewhitelabel.data.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pvsb.core.firebase.model.CreateUserRegistrationReqDTO
import com.pvsb.core.firebase.model.ProductDTO
import com.pvsb.core.firebase.model.UserFavoritesReqDTO
import com.pvsb.core.firebase.model.UserPersonalData
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class ProfileRepositoryImpl @Inject constructor() : ProfileRepository {

    private val db = Firebase.firestore

    override suspend fun getUsersRegistration(userId: String): UserPersonalData {
        return suspendCoroutine { continuation ->
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    document.toObject(CreateUserRegistrationReqDTO::class.java)?.let {
                        continuation.resumeWith(Result.success(it.personalData))
                    }
                }.addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }

    override suspend fun addProductToUserFavorites(userId: String, product: ProductDTO) {
        val docRef = db.collection("users").document(userId)
        docRef.update("products", FieldValue.arrayUnion(product))
    }
}