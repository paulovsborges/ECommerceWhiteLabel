package com.pvsb.ecommercewhitelabel.data.repository

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.pvsb.core.firestore.di.CartDocumentReference
import com.pvsb.core.firestore.model.CartProductsDTO
import com.pvsb.core.firestore.model.PopulateCartDTO
import com.pvsb.core.utils.Constants.FireStore.CART_COLLECTION
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class CartRepositoryImpl @Inject constructor(
    @CartDocumentReference private val document: DocumentReference
) : CartRepository {

    override suspend fun createCart(cartId: String, cart: PopulateCartDTO): Boolean {
        return suspendCoroutine { continuation ->

            document
                .collection(CART_COLLECTION)
                .document(cartId)
                .set(cart)
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(true))
                }
                .addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }

    override suspend fun addProductToCart(cartId: String, product: CartProductsDTO): Boolean {
        return suspendCoroutine { continuation ->

            document
                .collection(CART_COLLECTION)
                .document(cartId)
                .update("products", FieldValue.arrayUnion(product))
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(true))
                }
                .addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }
}