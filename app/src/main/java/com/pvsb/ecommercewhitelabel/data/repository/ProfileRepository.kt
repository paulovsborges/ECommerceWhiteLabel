package com.pvsb.ecommercewhitelabel.data.repository

import com.pvsb.core.firebase.model.ProductDTO
import com.pvsb.core.firebase.model.UserPersonalData

interface ProfileRepository {

    suspend fun getUsersRegistration(userId: String): UserPersonalData
    suspend fun addProductToUserFavorites(userId: String, product: ProductDTO)
}