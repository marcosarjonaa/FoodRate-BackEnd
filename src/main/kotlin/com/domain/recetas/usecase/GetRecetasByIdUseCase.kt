package com.domain.recetas.usecase

import com.domain.recetas.models.Recetas
import com.domain.recetas.repository.RecetasInterface

class GetRecetasByIdUseCase(val repository : RecetasInterface) {
    var idReceta : Int? = null

    suspend operator fun invoke(): Recetas? {
        return if (idReceta==null){
            null
        } else {
            repository.getRecetasById(idReceta!!)
        }
    }
}
