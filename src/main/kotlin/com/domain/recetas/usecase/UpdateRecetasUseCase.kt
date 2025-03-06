package com.domain.recetas.usecase

import com.domain.recetas.models.Recetas
import com.domain.recetas.models.UpdateRecetas
import com.domain.recetas.repository.RecetasInterface

class UpdateRecetasUseCase (val repository: RecetasInterface) {
    var receta : UpdateRecetas? = null
    var idReceta : Int? = null


    suspend operator fun invoke(): Boolean {
        return if(receta == null || idReceta == null){
            false
        } else {
            return repository.updateRecetas(receta!!, idReceta!!)
        }
    }
}