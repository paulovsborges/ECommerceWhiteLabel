package com.pvsb.ecommercewhitelabel.data.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pvsb.core.firestore.model.ProductDTO
import com.pvsb.core.utils.ResponseState
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class HomeRepositoryImpl @Inject constructor() : HomeRepository {

    private val db = Firebase.firestore
    private val homeRef = db.collection("data").document("home")

    override suspend fun getProducts(): ResponseState {
        val productsReference = homeRef.collection("products")

        return suspendCoroutine { continuation ->
            productsReference.get().addOnSuccessListener { documents ->
                val productsList = mutableListOf<ProductDTO>()
                documents.forEach { document ->
                    document.toObject(ProductDTO::class.java).run {
                        productsList.add(this)
                    }
                }
                continuation.resumeWith(Result.success(ResponseState.Complete.Success(productsList)))
            }
            productsReference.get().addOnFailureListener {
                continuation.resumeWith(Result.success(ResponseState.Complete.Fail(it)))
            }
        }
    }
}