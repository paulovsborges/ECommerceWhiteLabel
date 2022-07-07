package com.pvsb.ecommercewhitelabel.data.repository

import com.pvsb.core.firebase.model.CreateAccountReqDTO
import com.pvsb.core.firebase.model.CreateAccountResDTO
import com.pvsb.core.firebase.model.LoginReqDTO
import com.pvsb.core.firebase.model.LoginResDTO

interface AuthRepository {

    suspend fun doLogin(data: LoginReqDTO): LoginResDTO

    suspend fun createAccount(data: CreateAccountReqDTO): CreateAccountResDTO
}