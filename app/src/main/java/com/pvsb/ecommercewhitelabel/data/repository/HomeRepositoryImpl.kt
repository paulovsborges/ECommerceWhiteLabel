package com.pvsb.ecommercewhitelabel.data.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pvsb.core.model.ProductDTO
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class HomeRepositoryImpl @Inject constructor() : HomeRepository {

    private val db = Firebase.firestore
    private val homeRef = db.collection("data").document("home")

    override suspend fun getProducts(): List<ProductDTO> {
        val productsReference = homeRef.collection("products")

        return suspendCoroutine { continuation ->
            productsReference.get().addOnSuccessListener { documents ->
                val productsList = mutableListOf<ProductDTO>()
                documents.forEach { document ->
                    document.toObject(ProductDTO::class.java).run {
                        productsList.add(this)
                    }
                }
                continuation.resumeWith(Result.success(productsList))
            }
            productsReference.get().addOnFailureListener {
                continuation.resumeWith(Result.failure(it))
            }
        }
    }
}
