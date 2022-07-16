package com.pvsb.ecommercewhitelabel.data.repository

import com.pvsb.core.model.ProductDTO
import com.pvsb.core.model.UserPersonalData

interface ProfileRepository {

    suspend fun getUsersRegistration(userId: String): UserPersonalData
    suspend fun addProductToUserFavorites(userId: String, product: ProductDTO)
    suspend fun deleteProductToUserFavorites(userId: String, product: ProductDTO)
    suspend fun getFavoriteProducts(userId: String): List<ProductDTO>
}