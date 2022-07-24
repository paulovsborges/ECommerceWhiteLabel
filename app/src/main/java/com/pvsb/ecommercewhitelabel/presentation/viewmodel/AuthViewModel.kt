package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import com.pvsb.core.model.CreateAccountReqDTO
import com.pvsb.core.model.LoginReqDTO
import com.pvsb.core.utils.CoroutineViewModel
import com.pvsb.core.utils.ResponseState
import com.pvsb.core.utils.buildStateFlow
import com.pvsb.ecommercewhitelabel.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : CoroutineViewModel() {

    fun doLogin(data: LoginReqDTO): StateFlow<ResponseState> =
        buildStateFlow(authUseCase.doLogin(data))

    fun createAccount(data: CreateAccountReqDTO): StateFlow<ResponseState> =
        buildStateFlow(authUseCase.createAccount(data))
}
