package com.domain.recetas.mapping

import com.data.recetas.persistence.models.RecetaDao
import com.domain.recetas.models.Recetas
import com.domain.recetas.models.UpdateRecetas

fun Recetas.toUpdateRecetas() : UpdateRecetas {
    return UpdateRecetas(
        idRecetas= idRecetas,
        name = name,
        description= description ,
        image = image ,
        nota = nota
    )
}
fun UpdateRecetas.toRecetas(): Recetas {
    return Recetas(
        idRecetas = idRecetas!!,
        name = name!!,
        description = description!!,
        image = image!!,
        nota = nota!!,
        )
}
fun RecetaDao.toRecetas() : Recetas {
    val receta = Recetas(
        idRecetas = idReceta,
        name = name,
        description = description ,
        image = image ?: "",
        nota =nota

    )
    return receta
}
