package com.domain.usuario.models

import kotlinx.serialization.Serializable


@Serializable
data class Usuario (
    var dni: String,
    var name: String,
    var email: String,
    var password: String,
    var token: String
)