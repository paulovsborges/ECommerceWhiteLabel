package com.pvsb.ecommercewhitelabel.domain.usecase

import com.pvsb.core.firebase.model.CreateAccountReqDTO
import com.pvsb.core.firebase.model.CreateUserRegistrationReqDTO
import com.pvsb.core.firebase.model.LoginReqDTO
import com.pvsb.core.firebase.model.UserPersonalData
import com.pvsb.core.utils.ResponseState
import com.pvsb.ecommercewhitelabel.data.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend fun doLogin(data: LoginReqDTO): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)
        val res = repository.doLogin(data)
        emit(ResponseState.Complete.Success(res))
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }.flowOn(Dispatchers.IO)

    suspend fun createAccount(data: CreateAccountReqDTO): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)
        val res = repository.createAccount(data)
        val userData = UserPersonalData(res.userId, data.name, data.birth)
        val collectionData = CreateUserRegistrationReqDTO(userData)
        val userCollectionRes = repository.createUserCollection(collectionData)

        if (userCollectionRes) {
            emit(ResponseState.Complete.Success(res))
        } else {
            throw Exception()
        }
    }.catch {
        emit(ResponseState.Complete.Fail(it))
    }.flowOn(Dispatchers.IO)
}