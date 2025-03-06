package com.domain.recetas.usecase

import com.domain.recetas.repository.RecetasInterface

class DeleteRecetasUseCase(val repository: RecetasInterface) {
    var idReceta: Int?=null

    suspend operator fun invoke() : Boolean {
        return  if (idReceta == null){
            false
        } else{
            return repository.deleteRecetas(idReceta!!)
        }
    }
}
