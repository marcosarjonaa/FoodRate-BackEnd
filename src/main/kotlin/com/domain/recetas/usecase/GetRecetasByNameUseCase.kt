package com.domain.recetas.usecase

import com.domain.recetas.models.Recetas
import com.domain.recetas.repository.RecetasInterface
import com.ktor.ApplicationContext

class GetRecetasByNameUseCase (val repository: RecetasInterface) {
    var name : String? = null

    suspend operator fun invoke() : Recetas? {
        return if (name?.isNullOrBlank() == true)
            null
        else{
            val nombre = repository.getRecetasByName(name!!)
            nombre?.let{   name->
                if (!name.image.isNullOrBlank()) {
                    val local = ApplicationContext.context.environment.config.property("ktor.path.baseUrl").getString()
                    val relativePath = ApplicationContext.context.environment.config.property("ktor.path.images").getString()
                    name.image = "$relativePath/${name.idRecetas}/${name.image}"
                }
            }
            return nombre
        }
    }
}
