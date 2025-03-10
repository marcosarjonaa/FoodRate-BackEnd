package com.data.recetas.persistence.repository

import com.data.recetas.persistence.models.RecetaDao
import com.data.recetas.persistence.models.RecetaTable
import com.domain.recetas.mapping.toRecetas
import com.domain.recetas.models.Recetas
import com.domain.recetas.models.UpdateRecetas
import com.domain.recetas.repository.RecetasInterface
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class PersistenceRecetasRepository: RecetasInterface {

    override suspend fun getAllRecetas(): List<Recetas> {
        return newSuspendedTransaction(Dispatchers.IO) {
            RecetaDao.all().map { it.toRecetas() }
        }
    }

    override suspend fun getRecetasByName(name: String): Recetas? {
        return newSuspendedTransaction(Dispatchers.IO) {
            RecetaDao.find { RecetaTable.name eq name }
                .limit(1).map { it.toRecetas() }
                .firstOrNull()
        }
    }

    override suspend fun getRecetasById(idReceta: Int): Recetas? {
        return newSuspendedTransaction(Dispatchers.IO) {
            RecetaDao.find { RecetaTable.idReceta eq idReceta }
                .limit(1).map { it.toRecetas() }
                .firstOrNull()
        }
    }

    override suspend fun postRecetas(recetas: Recetas): Recetas? {
        return newSuspendedTransaction(Dispatchers.IO) {
            val recetaExistente = RecetaDao.find { RecetaTable.idReceta eq recetas.idRecetas }
                .limit(1).map { it.toRecetas() }.firstOrNull()

            if (recetaExistente == null) {
                RecetaDao.new {
                    this.idReceta = recetas.idRecetas ?: error("ID de receta no puede ser nulo")
                    this.name = recetas.name
                    this.description = recetas.description
                    this.image = recetas.image
                    this.nota = recetas.nota
                }
                null
            } else {
                recetaExistente
            }
        }
    }

    override suspend fun updateRecetas(updateReceta: UpdateRecetas, idReceta: Int): Recetas {
        return newSuspendedTransaction(Dispatchers.IO) {
            RecetaTable.update({ RecetaTable.idReceta eq idReceta }) { sttm ->
                updateReceta.name?.let { sttm[RecetaTable.name] = it }
                updateReceta.description?.let { sttm[RecetaTable.description] = it }
                updateReceta.image?.let { sttm[RecetaTable.image] = it }
                updateReceta.nota?.let { sttm[RecetaTable.nota] = it }
            }
            RecetaDao.find { RecetaTable.idReceta eq idReceta }
                .limit(1).map { it.toRecetas() }.first()
        }
    }

    override suspend fun deleteRecetas(idReceta: Int): Boolean {
        return newSuspendedTransaction(Dispatchers.IO) {
            val rowsDeleted = RecetaTable.deleteWhere { RecetaTable.idReceta eq idReceta }
            rowsDeleted == 1
        }
    }
}
