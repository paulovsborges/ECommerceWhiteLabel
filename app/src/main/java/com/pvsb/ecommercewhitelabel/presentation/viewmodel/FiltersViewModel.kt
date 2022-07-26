package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import com.pvsb.core.model.ProductFilterCategories
import com.pvsb.core.model.ProductFilters
import com.pvsb.core.model.ProductFiltersPrice
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

    private val selectedFilters = mutableListOf<ProductFilterCategories>()
    var minValue = 0.0
    var maxValue = 0.0
    var lastQuery = ""

    fun handleFilterSelection(item: ProductFilterCategories) {
        if (item.isChecked && !selectedFilters.contains(item)) {
            selectedFilters.add(item)
        } else {
            if (selectedFilters.contains(item)) {
                selectedFilters.remove(item)
            }
        }
    }

    fun getProducts(): StateFlow<ResponseState> {
        return buildStateFlow(filtersUseCase.getProducts(lastQuery, buildFilters()))
    }

    private fun buildFilters(): ProductFilters {
        return ProductFilters(
            price = ProductFiltersPrice(
                minValue = minValue,
                maxValue = maxValue
            ),
            categories = selectedFilters
        )
    }
}