package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.pvsb.core.model.ProductFilters
import com.pvsb.core.utils.CoroutineViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FiltersViewModel @Inject constructor(): CoroutineViewModel() {

    val selectedFilters = mutableListOf<ProductFilters>()

    fun handleFilterSelection(item: ProductFilters) {

        if (item.isChecked && !selectedFilters.contains(item)) {
            selectedFilters.add(item)
        } else {
            if (selectedFilters.contains(item)) {
                selectedFilters.remove(item)
            }
        }
    }
}