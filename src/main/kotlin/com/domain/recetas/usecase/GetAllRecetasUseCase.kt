package com.domain.recetas.usecase

import com.domain.recetas.models.Recetas
import com.domain.recetas.repository.RecetasInterface

class GetAllRecetasUseCase(val repository : RecetasInterface){
    suspend operator fun invoke(): List<Recetas> = repository.getAllRecetas()
}
