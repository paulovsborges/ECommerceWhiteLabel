package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.core.utils.ResponseState
import com.pvsb.ecommercewhitelabel.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HomeUseCase
) : ViewModel() {

    private val _homeContent = MutableStateFlow<ResponseState>(ResponseState.Init)
    val homeContent: StateFlow<ResponseState> = _homeContent

    fun getHomeData() {
        viewModelScope.launch {
            useCase.getProducts()
                .collectLatest {
                    _homeContent.value = it
                }
        }
    }
}