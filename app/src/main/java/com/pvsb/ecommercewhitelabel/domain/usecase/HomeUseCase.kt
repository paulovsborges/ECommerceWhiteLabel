package com.pvsb.ecommercewhitelabel.domain.usecase

import com.pvsb.core.utils.ResponseState
import com.pvsb.ecommercewhitelabel.data.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val repository: HomeRepository
) {

    fun getProducts(): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)
        val res = repository.getProducts()
        emit(ResponseState.Complete.Success(res))
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }
}