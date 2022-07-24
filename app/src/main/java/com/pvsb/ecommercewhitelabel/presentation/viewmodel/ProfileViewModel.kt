package com.pvsb.ecommercewhitelabel.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.pvsb.core.model.ProductDTO
import com.pvsb.core.model.UserAddressDTO
import com.pvsb.core.utils.CoroutineViewModel
import com.pvsb.core.utils.ResponseState
import com.pvsb.core.utils.buildStateFlow
import com.pvsb.ecommercewhitelabel.domain.usecase.NetworkUseCase
import com.pvsb.ecommercewhitelabel.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: ProfileUseCase,
    private val networkUseCase: NetworkUseCase
) : CoroutineViewModel() {

    fun getUserRegistration(userId: String): StateFlow<ResponseState> =
        buildStateFlow(useCase.getUsersRegistration(userId))

    fun addProductToUserFavorites(userId: String, product: ProductDTO) {
        viewModelScope.launch {
            useCase.addProductToUserFavorites(userId, product)
                .collect()
        }
    }

    fun deleteProductToUserFavorites(
        userId: String,
        product: ProductDTO
    ): StateFlow<ResponseState> =
        buildStateFlow(useCase.deleteProductToUserFavorites(userId, product))

    fun getFavoriteProducts(userId: String): StateFlow<ResponseState> =
        buildStateFlow(useCase.getFavoriteProducts(userId))

    fun getZipCodeInfo(postalCode: String): StateFlow<ResponseState> =
        buildStateFlow(networkUseCase.getZipCodeInfo(postalCode))

    fun saveAddress(userId: String, address: UserAddressDTO): StateFlow<ResponseState> =
        buildStateFlow(useCase.saveAddress(userId, address))

    fun deleteAddress(userId: String, address: UserAddressDTO): StateFlow<ResponseState> =
        buildStateFlow(useCase.deleteAddress(userId, address))

    fun getAddresses(userId: String): StateFlow<ResponseState> =
        buildStateFlow(useCase.getAddresses(userId))

    fun getOrders(userId: String): StateFlow<ResponseState> =
        buildStateFlow(useCase.getOrders(userId))
}