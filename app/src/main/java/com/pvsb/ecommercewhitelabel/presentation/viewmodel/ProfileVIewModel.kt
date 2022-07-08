package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.core.firebase.model.CreateAccountReqDTO
import com.pvsb.core.firebase.model.LoginReqDTO
import com.pvsb.core.utils.ResponseState
import com.pvsb.ecommercewhitelabel.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileVIewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _doLogin = MutableSharedFlow<ResponseState>()
    val doLogin: SharedFlow<ResponseState> = _doLogin

    private val _createAccount = MutableStateFlow<ResponseState>(ResponseState.Init)
    val createAccount: StateFlow<ResponseState> = _createAccount

    fun doLogin(data: LoginReqDTO) {
        viewModelScope.launch {
            authUseCase.doLogin(data).collectLatest {
                _doLogin.emit(it)
            }
        }
    }

    fun createAccount(data: CreateAccountReqDTO) {
        viewModelScope.launch {
            authUseCase.createAccount(data).collectLatest {
                _createAccount.value = it
            }
        }
    }
}