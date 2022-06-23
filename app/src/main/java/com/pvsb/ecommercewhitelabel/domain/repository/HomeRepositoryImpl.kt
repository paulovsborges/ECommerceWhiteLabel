package com.pvsb.ecommercewhitelabel.domain.repository

import com.google.firebase.firestore.DocumentReference
import com.pvsb.core.firestore.di.HomeDocumentReference
import com.pvsb.core.firestore.model.ProductDTO
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class HomeRepositoryImpl @Inject constructor(
    @HomeDocumentReference private val document: DocumentReference
) : HomeRepository {

    override suspend fun getProducts(): List<ProductDTO> {
        val productsReference = document.collection("products")

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