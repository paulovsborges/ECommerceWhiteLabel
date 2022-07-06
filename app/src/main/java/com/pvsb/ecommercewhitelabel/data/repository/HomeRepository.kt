package com.pvsb.ecommercewhitelabel.data.repository

import com.pvsb.core.utils.ResponseState

interface HomeRepository {

    suspend fun getProducts() : ResponseState
}