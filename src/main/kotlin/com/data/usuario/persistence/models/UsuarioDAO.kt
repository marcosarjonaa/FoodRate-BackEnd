package com.data.usuario.persistence.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UsuarioDAO (id : EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<UsuarioDAO>(UsuarioTable)
    var dni by UsuarioTable.dni
    var name by UsuarioTable.name
    var email by UsuarioTable.email
    var password by UsuarioTable.password
    var token by UsuarioTable.token
}