package com.pvsb.ecommercewhitelabel.domain.usecase

import com.pvsb.core.model.ProductDTO
import com.pvsb.core.model.ProductFilters
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

        val filteredList = products.filter {
            val title = it.title.lowercase()
            title.contains(search)
        }.distinctBy { it.title }

        if (filteredList.isEmpty()) {
            emit(ResponseState.Complete.Empty)
        } else {
            emit(ResponseState.Complete.Success(filteredList))
        }
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }.flowOn(Dispatchers.IO)

    fun applySearchFilters(
        search: String?,
        filters: ProductFilters,
        products: List<ProductDTO>
    ): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)

        val filteredList = mutableListOf<ProductDTO>()

        if (!search.isNullOrEmpty()) {
            filteredList.addAll(products.filter {
                val title = it.title.lowercase()
                title.contains(search)
            }.distinctBy { it.title })
        }

        if (filters.price.maxValue > 0.0) {
            filteredList.addAll(products.filter { it.price < filters.price.maxValue }
                .distinctBy { it.title })
        }

        if (filters.price.minValue > 0.0) {
            filteredList.addAll(products.filter { it.price > filters.price.minValue }
                .distinctBy { it.title })
        }

        if (filteredList.isEmpty()) {
            emit(ResponseState.Complete.Empty)
        } else {
            emit(ResponseState.Complete.Success(filteredList))
        }
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }.flowOn(Dispatchers.IO)
}