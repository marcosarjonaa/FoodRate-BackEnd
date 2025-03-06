package com.data.recetas.persistence.repository

import com.data.recetas.persistence.models.RecetaDao
import com.data.recetas.persistence.models.RecetaTable
import com.data.usuario.persistence.models.suspendTransaction
import com.domain.recetas.mapping.toRecetas
import com.domain.recetas.models.Recetas
import com.domain.recetas.models.UpdateRecetas
import com.domain.recetas.repository.RecetasInterface
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.update

class PersistenceRecetasRepository: RecetasInterface {
    override suspend fun getAllRecetas(): List<Recetas> {
        return suspendTransaction{
            RecetaDao.all().map {it.toRecetas() }
        }
    }

    override suspend fun getRecetasByName(name: String): Recetas? {
        return suspendTransaction{
            RecetaDao.find{
                RecetaTable.name eq name
            }.limit(1).map { it.toRecetas() }.firstOrNull()
        }
    }

    override suspend fun getRecetasById(idReceta: Int): Recetas? {
        return suspendTransaction{
            RecetaDao.find{
                RecetaTable.idReceta eq idReceta
            }
        }.limit(1).map { it.toRecetas() }.firstOrNull()
    }

    override suspend fun postRecetas(recetas: Recetas): Boolean {
        return suspendTransaction {
            val receta = RecetaDao.find { RecetaTable.idReceta eq recetas.idRecetas }
                .limit(1).map { it.toRecetas() }.firstOrNull()
            if (receta == null) {
                RecetaDao.new {
                    this.idReceta = recetas.idRecetas
                    this.name = recetas.name
                    this.description = recetas.description
                    this.image = recetas.image
                    this.nota = recetas.nota
                }
                true
            } else {
                false
            }
        }
    }



    override suspend fun updateRecetas(updateReceta: UpdateRecetas, idReceta: Int): Boolean {
        var num = 0
        try {
            suspendTransaction {
                num = RecetaTable
                    .update({
                        RecetaTable.idReceta eq idReceta
                    }) {sttm ->
                        updateReceta.name?.let { sttm[name] = it }
                        updateReceta.description?.let { sttm[description] = it }
                        updateReceta.image?.let { sttm[image] = it }
                        updateReceta.nota?.let { sttm[nota] = it }
                    }
            }
        } catch (e: Exception){
            e.printStackTrace()
            false
        }
        return num == 1
    }

    override suspend fun deleteRecetas(idReceta: Int): Boolean = suspendTransaction {
        val numero = RecetaTable
            .deleteWhere{ RecetaTable.idReceta eq idReceta }
        numero == 1
    }

}