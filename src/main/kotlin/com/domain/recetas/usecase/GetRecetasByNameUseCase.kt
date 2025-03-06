package com.domain.recetas.usecase

import com.domain.recetas.models.Recetas
import com.domain.recetas.repository.RecetasInterface

class GetRecetasByNameUseCase (val repository: RecetasInterface) {
    var name : String? = null

    suspend operator fun invoke() : Recetas? {
        return if(name == null){
            null
        }else {
            repository.getRecetasByName(name!!)
        }
    }
}
