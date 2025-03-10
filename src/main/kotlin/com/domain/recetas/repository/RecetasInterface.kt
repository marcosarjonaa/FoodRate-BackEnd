package com.domain.recetas.repository

import com.domain.recetas.models.Recetas
import com.domain.recetas.models.UpdateRecetas

/*
Como vamos a lanzar consultas a la BBDD, deben hacerse por medio de corrutinas.
Cuando desde una corrutina ejecuto un metodo, este tiene que estar definido como suspend.
 */

interface RecetasInterface {
    suspend fun getAllRecetas () : List <Recetas>

    suspend fun getRecetasByName ( name : String) : Recetas?

    suspend fun getRecetasById (idReceta: Int) : Recetas?

    suspend fun postRecetas(Recetas: Recetas) : Recetas?

    suspend fun updateRecetas(Recetas: UpdateRecetas, idReceta: Int) : Recetas

    suspend fun deleteRecetas(idReceta : Int) : Boolean
}