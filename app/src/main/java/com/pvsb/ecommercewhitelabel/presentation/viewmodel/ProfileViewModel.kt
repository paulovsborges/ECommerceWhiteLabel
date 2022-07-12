package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.core.utils.ResponseState
import com.pvsb.ecommercewhitelabel.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: ProfileUseCase
) : ViewModel() {

    private val _userRegistration = MutableStateFlow<ResponseState>(ResponseState.Init)
    val userRegistration: StateFlow<ResponseState> = _userRegistration

    fun getUserRegistration(userId: String) {
        viewModelScope.launch {
            useCase.getUsersRegistration(userId).collect {
                _userRegistration.value = it
            }
        }
    }
}