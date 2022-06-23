package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.ecommercewhitelabel.data.model.ProductDTO
import com.pvsb.ecommercewhitelabel.domain.HomeRepository
import com.pvsb.ecommercewhitelabel.domain.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVIewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val repository: HomeRepository
) : ViewModel() {

    private val _homeData = MutableLiveData<List<ProductDTO>>()
    val homeData: LiveData<List<ProductDTO>> = _homeData

    fun getHomeData() {
        viewModelScope.launch {
            val response = repository.getProducts()
            _homeData.value = response
        }
    }
}