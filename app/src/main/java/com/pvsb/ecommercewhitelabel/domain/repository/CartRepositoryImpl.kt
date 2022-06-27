package com.pvsb.ecommercewhitelabel.domain.repository

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.pvsb.core.firestore.di.CartDocumentReference
import com.pvsb.core.firestore.model.CreateCartDTO
import com.pvsb.core.utils.Constants.FireStore.CART_COLLECTION
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class CartRepositoryImpl @Inject constructor(
    @CartDocumentReference private val document: DocumentReference
) : CartRepository {

    override suspend fun createCart(cart: CreateCartDTO): String {
        return suspendCoroutine { continuation ->

            document
                .collection(CART_COLLECTION)
                .document(System.currentTimeMillis().toString())
                .set(cart)
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(cart.id))
                    Log.d(javaClass.simpleName, "createCart: Success")
                }
                .addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                    Log.d(javaClass.simpleName, "createCart: Error")
                }
        }
    }
}