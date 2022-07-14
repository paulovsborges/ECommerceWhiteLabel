package com.pvsb.core.firebase.model

import com.google.firebase.firestore.PropertyName
import kotlinx.serialization.Serializable

data class CreateAccountReqDTO(
    val email: String,
    val password: String,
    val name: String,
    val birth: String
)

data class CreateUserRegistrationReqDTO(
    @get:PropertyName("personal_data")
    @set:PropertyName("personal_data")
    var personalData: UserPersonalData = UserPersonalData()
)

@Serializable
data class CreateAccountResDTO(
    val email: String,
    val password: String,
    val userId: String
)
