package com.domain.recetas.usecase

import com.domain.recetas.models.Recetas
import com.domain.recetas.repository.RecetasInterface

class PostRecetasUseCase (val repository: RecetasInterface) {
    var receta: Recetas? = null

    suspend operator fun invoke() : Boolean {
        return repository.postRecetas(receta!!)
    }
}
