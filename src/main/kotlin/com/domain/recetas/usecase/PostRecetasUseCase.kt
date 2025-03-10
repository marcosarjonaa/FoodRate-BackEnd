package com.domain.recetas.usecase

import com.domain.recetas.infraestructure.Utils
import com.domain.recetas.models.Recetas
import com.domain.recetas.repository.RecetasInterface
import com.ktor.ApplicationContext

class PostRecetasUseCase (val repository: RecetasInterface) {
    var receta: Recetas? = null

    suspend operator fun invoke() : Recetas? {
        val r = repository.getRecetasById(receta!!.idRecetas)
        if (r!=null){
            return null
        }
        else{
            val isCreateDir = Utils.createDir(receta!!.idRecetas.toString())
            if (isCreateDir){
                val img = receta!!.image
                if (!img.isNullOrBlank()){
                    receta!!.image = Utils.createBase64ToImg(img, receta!!.idRecetas.toString()).toString()
                }
            }else{
                throw IllegalStateException("No se pudo crear el directorio de la receta. Puede que ya exista")
            }

            val new = repository.postRecetas(receta!!)

            new?.let{  rec->
                if (rec.image.isNotBlank())   { //Debemos setear la url correctamente.
                    val local = ApplicationContext.context.environment.config.property("ktor.urlPath.baseUrl").getString()
                    val relativePath = ApplicationContext.context.environment.config.property("ktor.urlPath.images").getString()
                    new.image = "$local/$relativePath/${new?.idRecetas}/${rec.image}"
                }
            }

            return new
        }


    }
}
