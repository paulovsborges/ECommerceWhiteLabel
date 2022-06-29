package com.pvsb.ecommercewhitelabel.data.repository

import com.google.firebase.firestore.DocumentReference
import com.pvsb.core.firestore.di.CartDocumentReference
import com.pvsb.core.firestore.model.CreateCartDTO
import com.pvsb.core.utils.Constants.FireStore.CART_COLLECTION
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class CartRepositoryImpl @Inject constructor(
    @CartDocumentReference private val document: DocumentReference
) : CartRepository {

    override suspend fun populateCart(cartId: String, cart: CreateCartDTO): String {
        return suspendCoroutine { continuation ->

            val cartDocument = System.currentTimeMillis().toString()

            document
                .collection(CART_COLLECTION)
                .document(cartDocument)
                .set(cart)
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(cartDocument))
                }
                .addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }
}