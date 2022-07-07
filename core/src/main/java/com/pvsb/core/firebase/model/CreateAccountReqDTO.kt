package com.pvsb.core.firebase.model

import kotlinx.serialization.Serializable

data class CreateAccountReqDTO(
    val email: String,
    val password: String
)

@Serializable
data class CreateAccountResDTO(
    val email: String,
    val password: String,
    val userId: String
)
