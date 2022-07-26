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

    fun getProducts(
        search: String?,
        filters: ProductFilters
    ): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)
        val res = homeRepository.getProducts()
        val filteredList = mutableListOf<ProductDTO>()

        if (!search.isNullOrEmpty()) {
            filteredList.addAll(res.filter {
                val title = it.title.lowercase()
                title.contains(search)
            })
        }

        if (filters.price.maxValue > 0.0) {
            filteredList.addAll(res.filter { it.price < filters.price.maxValue })
        }

        if (filters.price.minValue > 0.0) {
            filteredList.addAll(res.filter { it.price > filters.price.minValue })
        }

        if (filteredList.isEmpty()) {
            emit(ResponseState.Complete.Empty)
            emit(ResponseState.Complete.Success(res.distinctBy { it.title }))
        } else {
            emit(ResponseState.Complete.Success(filteredList.distinctBy { it.title }))
        }
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }.flowOn(Dispatchers.IO)
}