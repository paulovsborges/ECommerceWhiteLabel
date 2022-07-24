package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.pvsb.core.model.CreateAccountReqDTO
import com.pvsb.core.model.LoginReqDTO
import com.pvsb.core.utils.CoroutineViewModel
import com.pvsb.core.utils.ResponseState
import com.pvsb.core.utils.buildStateFlow
import com.pvsb.ecommercewhitelabel.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : CoroutineViewModel() {

    private val _createAccount = MutableStateFlow<ResponseState>(ResponseState.Init)
    val createAccount: StateFlow<ResponseState> = _createAccount

    fun doLogin(data: LoginReqDTO): StateFlow<ResponseState> =
        buildStateFlow(authUseCase.doLogin(data))

    fun createAccount(data: CreateAccountReqDTO) {
        viewModelScope.launch {
            authUseCase.createAccount(data).collectLatest {
                _createAccount.value = it
            }
        }
    }
}
