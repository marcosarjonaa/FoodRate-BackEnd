package com.data.recetas.persistence.models

import org.jetbrains.exposed.dao.id.IntIdTable

object  RecetaTable: IntIdTable("Receta") {
    val idReceta= integer("idReceta")
    val name = varchar("name", 100)
    val description = varchar("description", 255)
    val image = varchar("image", 1000)
    val nota = varchar("nota", 10)
}