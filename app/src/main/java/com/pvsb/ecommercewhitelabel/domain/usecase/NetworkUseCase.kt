package com.pvsb.ecommercewhitelabel.domain.usecase

import com.pvsb.core.utils.ResponseState
import com.pvsb.ecommercewhitelabel.data.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkUseCase @Inject constructor(
    private val repository: NetworkRepository
) {

    fun getZipCodeInfo(postalCode: String): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)
        val path = "$postalCode/json/"
        val res = repository.getPostalCodeInfo(path)
        emit(ResponseState.Complete.Success(res))
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }
}