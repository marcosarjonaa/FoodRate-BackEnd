package com.domain.recetas.usecase

import com.domain.recetas.infraestructure.Utils
import com.domain.recetas.models.Recetas
import com.domain.recetas.models.UpdateRecetas
import com.domain.recetas.repository.RecetasInterface

class UpdateRecetasUseCase (val repository: RecetasInterface) {
    var updateReceta : UpdateRecetas? = null
    var idReceta : Int? = null


    suspend operator fun invoke() : Recetas? {
        return if (updateReceta == null || idReceta == null) {
            null
        }else{
            try {
                updateReceta?.image?.let{  newImg->
                    val employee = repository.getRecetasById(idReceta!!)
                    employee?.let { employee ->
                        employee.image?.let{ oldImg->
                            Utils.deleteImage(updateReceta!!.idRecetas.toString(), oldImg)
                        }
                    }
                    val newImagenUrl = Utils.createBase64ToImg(newImg, idReceta!!.toString())
                    updateReceta!!.image = newImagenUrl
                }
                val uReceta = repository.updateRecetas(updateReceta!!, idReceta!!)
                return uReceta
            }catch (e: Exception){
                e.printStackTrace()
                null
            }
        }

    }
}