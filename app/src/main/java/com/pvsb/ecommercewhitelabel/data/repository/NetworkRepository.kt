package com.pvsb.ecommercewhitelabel.data.repository

import com.pvsb.core.model.PostalCodeResDTO

interface NetworkRepository {

    suspend fun getPostalCodeInfo(url: String): PostalCodeResDTO
}
