package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import com.pvsb.core.utils.CoroutineViewModel
import com.pvsb.core.utils.ResponseState
import com.pvsb.core.utils.buildStateFlow
import com.pvsb.ecommercewhitelabel.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HomeUseCase
) : CoroutineViewModel() {

    fun getHomeData(): StateFlow<ResponseState> = buildStateFlow(useCase.getProducts())
}