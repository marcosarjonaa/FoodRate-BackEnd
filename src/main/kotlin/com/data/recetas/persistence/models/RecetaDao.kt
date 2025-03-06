package com.data.recetas.persistence.models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RecetaDao (id : EntityID<Int>) :  IntEntity(id){
    companion object : IntEntityClass<RecetaDao>(RecetaTable) //heredamos todos los métodos estáticos de acceso a los datos de la BBDD
    var idReceta by RecetaTable.idReceta
    var name by RecetaTable.name
    var description by RecetaTable.description
    var nota by RecetaTable.nota
    var image by RecetaTable.image

}