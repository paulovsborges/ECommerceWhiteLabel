package com.pvsb.ecommercewhitelabel.data.repository

import com.pvsb.core.firebase.model.UserPersonalData

interface ProfileRepository {

    suspend fun getUsersRegistration(userId: String): UserPersonalData
}