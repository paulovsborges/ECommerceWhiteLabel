package com.pvsb.core.model

import kotlinx.serialization.Serializable

data class LoginReqDTO(
    val email: String,
    val password: String
)

@Serializable
data class LoginResDTO(

    val userData : UserPersonalData
)
