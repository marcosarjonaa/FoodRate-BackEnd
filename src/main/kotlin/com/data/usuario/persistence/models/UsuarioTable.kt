package com.data.usuario.persistence.models

import com.data.recetas.persistence.models.RecetaTable.nullable
import com.data.recetas.persistence.models.RecetaTable.uniqueIndex
import com.data.recetas.persistence.models.RecetaTable.varchar
import org.jetbrains.exposed.dao.id.IntIdTable

object UsuarioTable : IntIdTable("Usuario") {

    val dni = varchar("dni", 20).uniqueIndex() // Actua como id de usuario
    val email = varchar("email", 255)
    val password = varchar("password", 255)
    val name = varchar("nombre", 100)
    val token = varchar("token", 255).nullable()
}