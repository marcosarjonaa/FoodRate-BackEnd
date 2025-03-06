package com.domain.usuario.models

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUsuario (
    var dni: String?=null,
    var name: String?=null,
    var email: String?=null,
    var password: String?=null,
    var token:String?=null
)