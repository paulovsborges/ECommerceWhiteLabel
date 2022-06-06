package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.ecommercewhitelabel.data.model.MainHomeModel
import com.pvsb.ecommercewhitelabel.domain.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVIewModel @Inject constructor(private val homeUseCase: HomeUseCase) : ViewModel() {

    private val _homeData = MutableLiveData<List<MainHomeModel>>()
    val homeData: LiveData<List<MainHomeModel>> = _homeData

    fun getHomeData() {
        viewModelScope.launch {
            homeUseCase.mockData().collect {
                _homeData.value = it
            }
        }
    }
}