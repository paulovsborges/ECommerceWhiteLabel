package com.pvsb.ecommercewhitelabel.domain.usecase

import com.pvsb.core.utils.ResponseState
import com.pvsb.ecommercewhitelabel.BuildConfig
import com.pvsb.ecommercewhitelabel.data.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkUseCase @Inject constructor(
    private val repository: NetworkRepository
) {

    suspend fun getPostalCodeInfo(postalCode: String): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)
        val completeUrl = "${BuildConfig.BASE_URL_POSTAL_CODE}$postalCode/json/"
        val res = repository.getPostalCodeInfo(completeUrl)
        emit(ResponseState.Complete.Success(res))
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }
}