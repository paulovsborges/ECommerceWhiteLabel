package com.pvsb.ecommercewhitelabel.data.repository

import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pvsb.core.model.CartProductsDTO
import com.pvsb.core.model.PopulateCartDTO
import com.pvsb.core.utils.Constants.FireStore.CART_COLLECTION
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class CartRepositoryImpl @Inject constructor() : CartRepository {

    private val db = Firebase.firestore

    override suspend fun createCart(cartId: String, cart: PopulateCartDTO, value: Double): Boolean {
        return suspendCoroutine { continuation ->

            val docRef = db
                .collection("data/")
                .document("cart/")
                .collection(CART_COLLECTION)
                .document(cartId)

            db.runTransaction { transaction ->
                transaction.set(docRef, cart)

                transaction.update(
                    docRef,
                    "total",
                    FieldValue.increment(value)
                )
            }
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(true))
                }
                .addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }

    override suspend fun addProductToCart(
        cartId: String,
        product: CartProductsDTO,
        value: Double
    ): Boolean {
        return suspendCoroutine { continuation ->

            val docRef = db
                .collection("data/")
                .document("cart/")
                .collection(CART_COLLECTION)
                .document(cartId)

            db.runTransaction { transaction ->
                val snapShot = transaction.get(docRef)
                val currentValue = snapShot.getDouble("total")
                val data = snapShot.toObject(PopulateCartDTO::class.java)
                val products = data?.products?.map { it.product.title }

                if (products?.contains(product.product.title) == true) {
                    throw Exception("Product is already added on the cart")
                } else {
                    if (currentValue != null) {
                        transaction.update(
                            docRef,
                            "total",
                            FieldValue.increment(value)
                        )
                    } else {
                        transaction.update(docRef, " total", value)
                    }

                    transaction.update(docRef, "products", FieldValue.arrayUnion(product))
                }
            }
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(true))
                }
                .addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }

    override suspend fun getCartContent(cartId: String): PopulateCartDTO {
        return suspendCoroutine { continuation ->
            db.collection("data/")
                .document("cart/")
                .collection(CART_COLLECTION)
                .document(cartId)
                .get()
                .addOnSuccessListener { document ->
                    val data = document.toObject(PopulateCartDTO::class.java)

                    data?.let {
                        continuation.resumeWith(Result.success(data))
                    } ?: kotlin.run {
                        continuation.resumeWith(Result.failure(Exception("Error to load data")))
                    }
                }
                .addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }

    override suspend fun deleteProduct(
        cartId: String,
        product: CartProductsDTO,
        value: Double
    ): Boolean {
        return suspendCoroutine { continuation ->
            val docRef = db
                .collection("data/")
                .document("cart/")
                .collection(CART_COLLECTION)
                .document(cartId)

            db.runTransaction { transaction ->
                val snapShot = transaction.get(docRef)
                val data = snapShot.toObject(PopulateCartDTO::class.java)

                data?.products?.find {
                    it.product == product.product
                }?.let {

                    FieldPath.of("products")

                    val productToDelete =
                        mutableMapOf<String, Any>("products" to FieldValue.arrayRemove(it))

                    transaction.update(docRef, productToDelete)
                }

                data?.total?.let { total ->

                    val newTotal = total - value

                    transaction.update(
                        docRef,
                        "total",
                        newTotal
                    )
                }
            }.addOnSuccessListener {
                continuation.resumeWith(Result.success(true))
            }.addOnFailureListener {
                continuation.resumeWith(Result.failure(it))
            }
        }
    }
}
