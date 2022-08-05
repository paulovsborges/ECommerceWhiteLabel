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
            emit(ResponseState.Complete.Success(res.distinctBy { it.title }))
        }
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }.flowOn(Dispatchers.IO)

    fun doSearch(
        search: String?,
        filters: ProductFilters
    ): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)
        val res = homeRepository.getProducts()
        val filteredList = mutableListOf<ProductDTO>()
        filteredList.addAll(res)

        if (filters.categories.isNotEmpty()) {
            val listByCategories = mutableListOf<ProductDTO>()

            filters.categories.forEach { category ->
                listByCategories.addAll(filteredList.filter { it.categoryId == category.id })
            }

            if (listByCategories.isNotEmpty()) {
                filteredList.clear()
                filteredList.addAll(listByCategories)
            }
        }

        if (!search.isNullOrEmpty()) {
            filteredList.removeAll {
                val title = it.title.lowercase()
                !title.contains(search)
            }
        }

        when {
            filters.price.maxValue > 0.0 && filters.price.minValue > 0.0 -> {
                filteredList.removeAll { it.price !in filters.price.minValue..filters.price.maxValue }
            }
            filters.price.minValue > 0.0 -> {
                filteredList.removeAll { it.price < filters.price.minValue }
            }
            filters.price.maxValue > 0.0 -> {
                filteredList.removeAll { it.price > filters.price.maxValue }
            }
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
