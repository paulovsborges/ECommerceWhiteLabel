package com.pvsb.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostalCodeResDTO(
    @SerialName("cep")
    val postalCode: String,
    @SerialName("logradouro")
    val street: String,
    @SerialName("uf")
    val state: String,
    @SerialName("bairro")
    val neighbour: String,
    @SerialName("localidade")
    val city: String
)