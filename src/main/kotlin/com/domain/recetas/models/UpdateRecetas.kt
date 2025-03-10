package com.domain.recetas.models

import kotlinx.serialization.Serializable

/*
Sólo para serializar en consultas
patch
 */
@Serializable
data class UpdateRecetas (
    val idRecetas : Int?=null,
    val name : String?=null,
    val description: String?=null,
    var image: String?=null,
    val nota: String?=null
)