package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.pvsb.core.model.CreateAccountReqDTO
import com.pvsb.core.model.LoginReqDTO
import com.pvsb.core.utils.CoroutineViewModel
import com.pvsb.core.utils.ResponseState
import com.pvsb.core.utils.buildStateFlow
import com.pvsb.ecommercewhitelabel.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : CoroutineViewModel() {

    /**
     * Here i used this approach to prevent the state flow to replay the last cached emission on the ui
     * and still keep the job instance alive
     */
    fun doLogin(data: LoginReqDTO): SharedFlow<ResponseState> {
        val sharedFlow = MutableSharedFlow<ResponseState>(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

        authUseCase.doLogin(data).onEach {
            sharedFlow.emit(it)
        }.launchIn(viewModelScope)

        return sharedFlow
    }

    fun createAccount(data: CreateAccountReqDTO): StateFlow<ResponseState> =
        buildStateFlow(authUseCase.createAccount(data))

    fun changePassword(oldPassword: String, newPassword: String): StateFlow<ResponseState> =
        buildStateFlow(authUseCase.changePassword(oldPassword, newPassword))
}
