package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.core.model.ProductDTO
import com.pvsb.core.model.UserAddressDTO
import com.pvsb.core.utils.ResponseState
import com.pvsb.ecommercewhitelabel.domain.usecase.NetworkUseCase
import com.pvsb.ecommercewhitelabel.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: ProfileUseCase,
    private val networkUseCase: NetworkUseCase
) : ViewModel() {

    private val _userRegistration = MutableStateFlow<ResponseState>(ResponseState.Init)
    val userRegistration: StateFlow<ResponseState> = _userRegistration

    private val _userFavoriteProducts = MutableStateFlow<ResponseState>(ResponseState.Init)
    val userFavoriteProducts: StateFlow<ResponseState> = _userFavoriteProducts

    private val _postalCodeInfo = MutableStateFlow<ResponseState>(ResponseState.Init)
    val postalCodeInfo: StateFlow<ResponseState> = _postalCodeInfo

    private val _saveAddress = MutableStateFlow<ResponseState>(ResponseState.Init)
    val saveAddress: StateFlow<ResponseState> = _saveAddress

    private val _addresses = MutableStateFlow<ResponseState>(ResponseState.Init)
    val addresses: StateFlow<ResponseState> = _addresses

    fun getUserRegistration(userId: String) {
        viewModelScope.launch {
            useCase.getUsersRegistration(userId).collect {
                _userRegistration.value = it
            }
        }
    }

    fun addProductToUserFavorites(userId: String, product: ProductDTO) {
        viewModelScope.launch {
            useCase.addProductToUserFavorites(userId, product)
                .collect()
        }
    }

    fun deleteProductToUserFavorites(userId: String, product: ProductDTO) {
        viewModelScope.launch {
            useCase.deleteProductToUserFavorites(userId, product)
                .collect {
                    getFavoriteProducts(userId)
                }
        }
    }

    fun getFavoriteProducts(userId: String) {
        viewModelScope.launch {
            useCase.getFavoriteProducts(userId).collect {
                _userFavoriteProducts.value = it
            }
        }
    }

    fun getPostalCodeInfo(postalCode: String) {
        viewModelScope.launch {
            networkUseCase.getPostalCodeInfo(postalCode).collect {
                _postalCodeInfo.value = it
            }
        }
    }

    fun saveAddress(userId: String, address: UserAddressDTO) {
        viewModelScope.launch {
            useCase.saveAddress(userId, address).collect {
                _saveAddress.value = it
            }
        }
    }

    fun deleteAddress(userId: String, address: UserAddressDTO) {
        viewModelScope.launch {
            useCase.deleteAddress(userId, address).collect {
                getAddresses(userId)
            }
        }
    }

    fun getAddresses(userId: String) {
        viewModelScope.launch {
            useCase.getAddresses(userId).collect {
                _addresses.value = it
            }
        }
    }
}