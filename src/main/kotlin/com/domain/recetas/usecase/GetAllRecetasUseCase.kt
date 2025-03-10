package com.domain.recetas.usecase

import com.domain.recetas.models.Recetas
import com.domain.recetas.repository.RecetasInterface
import com.ktor.ApplicationContext

class GetAllRecetasUseCase(val repository : RecetasInterface){
    suspend operator fun invoke(): List<Recetas> {

        val listRecetas = repository.getAllRecetas()  //toda la lista de empleados
        return listRecetas.map{ receta->
            if (!receta.image.isNullOrBlank()){  //si la imagen no es nula
                val local = ApplicationContext.context.environment.config.property("ktor.urlPath.baseUrl").getString()
                val relativePath = ApplicationContext.context.environment.config.property("ktor.urlPath.images").getString()
                receta.image = "$local/$relativePath/${receta.idRecetas}/${receta.image}"
            }
            receta
        }
    }
}
