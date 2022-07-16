package com.pvsb.ecommercewhitelabel.data.repository

import com.pvsb.core.model.*

interface AuthRepository {

    suspend fun doLogin(data: LoginReqDTO): UserPersonalData

    suspend fun createAccount(data: CreateAccountReqDTO): CreateAccountResDTO

    suspend fun createUserCollection(data: CreateUserRegistrationReqDTO): Boolean
}