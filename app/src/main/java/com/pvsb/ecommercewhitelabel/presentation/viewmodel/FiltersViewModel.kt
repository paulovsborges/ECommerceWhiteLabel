package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import com.pvsb.core.model.ProductDTO
import com.pvsb.core.model.ProductFilterCategories
import com.pvsb.core.model.ProductFilters
import com.pvsb.core.utils.CoroutineViewModel
import com.pvsb.core.utils.ResponseState
import com.pvsb.core.utils.buildStateFlow
import com.pvsb.ecommercewhitelabel.domain.usecase.FiltersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FiltersViewModel @Inject constructor(
    private val filtersUseCase: FiltersUseCase
) : CoroutineViewModel() {

    val selectedFilters = mutableListOf<ProductFilterCategories>()

    fun handleFilterSelection(item: ProductFilterCategories) {

        if (item.isChecked && !selectedFilters.contains(item)) {
            selectedFilters.add(item)
        } else {
            if (selectedFilters.contains(item)) {
                selectedFilters.remove(item)
            }
        }
    }

    val products = mutableListOf<ProductDTO>()
    var lastQuery = ""

    fun getProducts(): StateFlow<ResponseState> = buildStateFlow(filtersUseCase.getProducts())

    fun searchProducts(search: String): StateFlow<ResponseState> =
        buildStateFlow(filtersUseCase.searchProducts(search, products))

    fun applySearchFilters(filters: ProductFilters): StateFlow<ResponseState> =
        buildStateFlow(filtersUseCase.applySearchFilters(lastQuery, filters, products))
}