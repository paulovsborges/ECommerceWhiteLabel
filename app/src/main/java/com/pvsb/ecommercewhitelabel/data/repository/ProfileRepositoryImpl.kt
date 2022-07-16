package com.pvsb.ecommercewhitelabel.data.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pvsb.core.model.CreateUserRegistrationReqDTO
import com.pvsb.core.model.ProductDTO
import com.pvsb.core.model.UserFavoritesReqDTO
import com.pvsb.core.model.UserPersonalData
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

    override suspend fun deleteProductToUserFavorites(userId: String, product: ProductDTO) {
        val docRef = db.collection("users").document(userId)
        docRef.update("products", FieldValue.arrayRemove(product))
    }

    override suspend fun getFavoriteProducts(userId: String): List<ProductDTO> {
        val docRef = db.collection("users").document(userId)
        return suspendCoroutine { continuation ->
            docRef.get()
                .addOnSuccessListener { document ->
                    document.toObject(UserFavoritesReqDTO::class.java)?.let {
                        continuation.resumeWith(Result.success(it.products))
                    }
                }.addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }
}