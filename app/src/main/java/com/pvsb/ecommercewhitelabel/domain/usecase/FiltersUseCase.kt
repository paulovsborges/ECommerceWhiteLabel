package com.pvsb.ecommercewhitelabel.domain.usecase

import com.pvsb.core.model.ProductDTO
import com.pvsb.core.utils.ResponseState
import com.pvsb.ecommercewhitelabel.data.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FiltersUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {

    fun getProducts(): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)
        val res = homeRepository.getProducts()
        if (res.isEmpty()) {
            emit(ResponseState.Complete.Empty)
        } else {
            emit(ResponseState.Complete.Success(res))
        }
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }.flowOn(Dispatchers.IO)

    fun searchProducts(search: String, products: List<ProductDTO>): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)

        val filteredList = products.filter { it.title == search }

        if (filteredList.isEmpty()) {
            emit(ResponseState.Complete.Empty)
        } else {
            emit(ResponseState.Complete.Success(filteredList))
        }
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }.flowOn(Dispatchers.IO)
}