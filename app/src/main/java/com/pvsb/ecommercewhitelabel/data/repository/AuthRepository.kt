package com.pvsb.ecommercewhitelabel.data.repository

import com.pvsb.core.model.CreateAccountReqDTO
import com.pvsb.core.model.CreateAccountResDTO
import com.pvsb.core.model.CreateUserRegistrationReqDTO
import com.pvsb.core.model.LoginReqDTO
import com.pvsb.core.model.UserPersonalData

interface AuthRepository {

    suspend fun doLogin(data: LoginReqDTO): UserPersonalData
    suspend fun createAccount(data: CreateAccountReqDTO): CreateAccountResDTO
    suspend fun createUserCollection(data: CreateUserRegistrationReqDTO): Boolean
    suspend fun changePassword(oldPassword: String, newPassword: String)
}
