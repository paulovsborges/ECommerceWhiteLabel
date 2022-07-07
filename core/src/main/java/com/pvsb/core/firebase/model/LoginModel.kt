package com.pvsb.core.firebase.model

import kotlinx.serialization.Serializable

data class LoginReqDTO(
    val email: String,
    val password: String
)

@Serializable
data class LoginResDTO(
    val email: String,
    val password: String,
    val userId: String
)
