package com.domain.recetas.models
import kotlinx.serialization.Serializable

@Serializable
data class Recetas(
    val idRecetas : Int,
    val name : String,
    val description: String,
    var image: String,
    val nota: String
)
