package com.pvsb.ecommercewhitelabel.data.repository

import com.pvsb.core.model.PostalCodeResDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val client: HttpClient
) : NetworkRepository {

    override suspend fun getPostalCodeInfo(url: String): PostalCodeResDTO {
        return client.get {
            url(url)
        }
    }
}
