package com.pvsb.ecommercewhitelabel.data.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pvsb.core.model.*
import com.pvsb.core.utils.Constants
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

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
        docRef.update("favorites", FieldValue.arrayUnion(product))
    }

    override suspend fun deleteProductFromUserFavorites(userId: String, product: ProductDTO) {
        val docRef = db.collection("users").document(userId)
        docRef.update("favorites", FieldValue.arrayRemove(product))
    }

    override suspend fun getFavoriteProducts(userId: String): List<ProductDTO> {
        val docRef = db.collection("users").document(userId)
        return suspendCoroutine { continuation ->
            docRef.get()
                .addOnSuccessListener { document ->
                    document.toObject(UserFavoritesResDTO::class.java)?.let {
                        continuation.resumeWith(Result.success(it.favorites))
                    }
                }.addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }

    override suspend fun saveAddress(userId: String, address: UserAddressDTO): Boolean {
        return suspendCoroutine { continuation ->

            val docRef = db.collection("users").document(userId)
            docRef.update("addresses", FieldValue.arrayUnion(address))
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(true))
                }
                .addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }

    override suspend fun deleteAddress(userId: String, address: UserAddressDTO): Boolean {
        return suspendCoroutine { continuation ->

            val docRef = db.collection("users").document(userId)
            docRef.update("addresses", FieldValue.arrayRemove(address))
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(true))
                }
                .addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }

    override suspend fun getAddresses(userId: String): List<UserAddressDTO> {
        val docRef = db.collection("users").document(userId)
        return suspendCoroutine { continuation ->
            docRef.get()
                .addOnSuccessListener { document ->
                    document.toObject(UserAddressesResDTO::class.java)?.let {
                        continuation.resumeWith(Result.success(it.addresses))
                    }
                }.addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }

    override suspend fun registerOder(
        cartId: String,
        userId: String,
        order: OderModelReqDTO
    ): Boolean {
        return suspendCoroutine { continuation ->
            val docRef = db.collection("users").document(userId)

            val cartRef = db.collection("data/")
                .document("cart/")
                .collection(Constants.FireStore.CART_COLLECTION)
                .document(cartId)

            db.runTransaction {
                it.get(docRef).toObject(OderModelResDTO::class.java)?.let { obj ->
                    order.orderId = (Random.nextInt(1, 10000) + obj.orders.size).toString()
                } ?: kotlin.run {
                    order.orderId = Random.nextInt(1, 10000).toString()
                }

                it.update(docRef, "orders", FieldValue.arrayUnion(order))
                it.delete(cartRef)
            }.addOnSuccessListener {
                continuation.resumeWith(Result.success(true))
            }.addOnFailureListener {
                continuation.resumeWith(Result.failure(it))
            }
        }
    }

    override suspend fun getOrders(userId: String): OderModelResDTO {
        val docRef = db.collection("users").document(userId)
        return suspendCoroutine { continuation ->
            docRef.get()
                .addOnSuccessListener { document ->
                    document.toObject(OderModelResDTO::class.java)?.let {
                        continuation.resumeWith(Result.success(it))
                    }
                }.addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }
}