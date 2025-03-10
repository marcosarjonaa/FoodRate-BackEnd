package com.domain.recetas.usecase

import com.data.recetas.persistence.repository.PersistenceRecetasRepository
import com.domain.recetas.models.Recetas
import com.domain.recetas.models.UpdateRecetas
import org.slf4j.LoggerFactory
import java.util.logging.Logger

import com.domain.usuario.usecase.ProviderUseCase.logger

object RecetasProviderUseCase {
    val repository = PersistenceRecetasRepository()

    val getAllRecetasUseCase = GetAllRecetasUseCase(repository)
    val getRecetasByNameUseCase = GetRecetasByNameUseCase(repository)
    val getRecetasByIdUseCase = GetRecetasByIdUseCase(repository)
    val postRecetasUseCase = PostRecetasUseCase(repository)
    val updateRecetasUseCase = UpdateRecetasUseCase(repository)
    val deleteRecetasUseCase = DeleteRecetasUseCase(repository)

    suspend fun getAllRecetas() = getAllRecetasUseCase

    suspend fun getRecetasByName(name: String): Recetas?{
        if (name== null){
            logger.warn("El nombre de receta está vacío")
            return null
        }
        getRecetasByNameUseCase.name=name
        val receta = getRecetasByIdUseCase()
        return if(receta == null){
            logger.warn("No hay ninguna receta con ese nombre")
            null
        }else {
            receta
        }
    }

    suspend fun getRecetasById(idRecetas: Int): Recetas?{
        if (idRecetas==null){
            logger.warn("El id de la receta está vacío")
            return null
        }
        getRecetasByIdUseCase.idReceta=idRecetas
        val receta = getRecetasByIdUseCase()
        return if(receta == null){
            logger.warn("No hay ninguna receta con esa id")
            null
        } else {
            receta
        }
    }

    suspend fun postRecetas(receta: Recetas): Recetas?{
        if (receta == null){
            logger.warn("Faltan datos del monumento")
            return null
        }
        postRecetasUseCase.receta= receta
        var respuesta = postRecetasUseCase()
        return if (respuesta == null){
            logger.warn("No se ha podido insertar")
            null
        } else {
            receta
        }
    }

    suspend fun updateRecetas(idRecetas: Int, updateRecetas: UpdateRecetas?): Recetas?{
        if (updateRecetas == null){
            logger.warn("No hay datos en esa receta")
            return null
        }

        updateRecetasUseCase.idReceta = idRecetas
        updateRecetasUseCase.updateReceta= updateRecetas
        return updateRecetasUseCase()
    }

    suspend fun deleteRecetas(idRecetas: Int): Boolean{
        deleteRecetasUseCase.idReceta=idRecetas
        return deleteRecetasUseCase()
    }
}