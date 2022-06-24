package com.pvsb.ecommercewhitelabel.domain.repository

import com.google.firebase.firestore.DocumentReference
import com.pvsb.core.firestore.di.CartDocumentReference
import com.pvsb.core.firestore.model.CreateCartDTO
import com.pvsb.core.utils.Constants.FireStore.ROOT_COLLECTION
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class CartRepositoryImpl @Inject constructor(
    @CartDocumentReference private val document: DocumentReference
) : CartRepository {

    override suspend fun createCart(cart: CreateCartDTO): String {
        return suspendCoroutine { continuation ->

            document
                .collection(ROOT_COLLECTION)
                .document("cart/${System.currentTimeMillis()}")
                .set(cart)
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(cart.id))
                }
                .addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }
}