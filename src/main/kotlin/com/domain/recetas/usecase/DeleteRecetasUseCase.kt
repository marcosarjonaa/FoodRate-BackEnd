package com.domain.recetas.usecase

import com.domain.recetas.infraestructure.Utils
import com.domain.recetas.repository.RecetasInterface

class DeleteRecetasUseCase(val repository: RecetasInterface) {
    var idReceta: Int?=null

    suspend operator fun invoke() : Boolean {
        return  if (idReceta == null){
            false
        } else{
            val receta = repository.getRecetasById(idReceta!!)
            receta?.let { r ->
                r.image?.let {img ->
                    Utils.deleteImage(idReceta.toString()!!, img)
                    Utils.deleteDirectory(idReceta.toString()!!)
                }
                return repository.deleteRecetas(idReceta!!)
            }
            return false
        }
    }
}
