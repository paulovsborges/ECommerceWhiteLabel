package com.pvsb.ecommercewhitelabel.domain.usecase

import com.pvsb.core.model.ProductDTO
import com.pvsb.core.utils.ResponseState
import com.pvsb.ecommercewhitelabel.data.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    suspend fun getUsersRegistration(
        userId: String
    ): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)
        val res = repository.getUsersRegistration(userId)
        emit(ResponseState.Complete.Success(res))
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }.flowOn(Dispatchers.IO)

    suspend fun addProductToUserFavorites(
        userId: String,
        product: ProductDTO
    ): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)
        val res = repository.addProductToUserFavorites(userId, product)
        emit(ResponseState.Complete.Success(res))
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }.flowOn(Dispatchers.IO)

    suspend fun deleteProductToUserFavorites(
        userId: String,
        product: ProductDTO
    ): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)
        val res = repository.deleteProductToUserFavorites(userId, product)
        emit(ResponseState.Complete.Success(res))
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }.flowOn(Dispatchers.IO)

    suspend fun getFavoriteProducts(
        userId: String
    ): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)
        val res = repository.getFavoriteProducts(userId)

        if (res.isEmpty()) {
            emit(ResponseState.Complete.Empty)
        } else {
            emit(ResponseState.Complete.Success(res))
        }
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }.flowOn(Dispatchers.IO)
}