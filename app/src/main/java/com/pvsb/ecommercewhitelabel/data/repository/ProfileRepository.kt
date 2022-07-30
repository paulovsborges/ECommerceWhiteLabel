package com.pvsb.ecommercewhitelabel.data.repository

import com.pvsb.core.model.*

interface ProfileRepository {

    suspend fun getUsersRegistration(userId: String): UserPersonalData
    suspend fun addProductToUserFavorites(userId: String, product: ProductDTO)
    suspend fun deleteProductFromUserFavorites(userId: String, product: ProductDTO)
    suspend fun getFavoriteProducts(userId: String): List<ProductDTO>
    suspend fun saveAddress(userId: String, address: UserAddressDTO): Boolean
    suspend fun deleteAddress(userId: String, address: UserAddressDTO): Boolean
    suspend fun getAddresses(userId: String): List<UserAddressDTO>
    suspend fun registerOder(cartId: String, userId: String, order: OderModelReqDTO): Boolean
    suspend fun getOrders(userId: String): OderModelResDTO
}
