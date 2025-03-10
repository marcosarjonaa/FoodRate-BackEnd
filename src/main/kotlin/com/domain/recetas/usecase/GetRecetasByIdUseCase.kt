package com.domain.recetas.usecase

import com.domain.recetas.models.Recetas
import com.domain.recetas.repository.RecetasInterface
import com.ktor.ApplicationContext
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlinx.coroutines.Dispatchers

class GetRecetasByIdUseCase(private val repository: RecetasInterface) {

    var idReceta: Int? = null

    suspend operator fun invoke(): Recetas? {
        val recetaId = idReceta ?: return null
        val receta = repository.getRecetasById(recetaId)
        receta?.let {
            if (!receta.image.isNullOrBlank()) {
                val local = ApplicationContext.context.environment.config
                    .property("ktor.urlPath.baseUrl").getString()

                val relativePath = ApplicationContext.context.environment.config
                    .property("ktor.urlPath.images").getString()

                receta.image = "$local/$relativePath/$recetaId/${receta.image}"
            }
        }
        return receta
    }
}
