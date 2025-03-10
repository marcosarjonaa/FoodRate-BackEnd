package com.ktor.routing

import com.domain.recetas.mapping.toRecetas
import com.domain.recetas.models.Recetas
import com.domain.recetas.models.UpdateRecetas
import com.domain.recetas.usecase.RecetasProviderUseCase
import com.domain.usuario.usecase.ProviderUseCase
import com.ktor.validateToken
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun Route.recetasRouting(){
    route("/recetas"){
        authenticate("jwt-auth") {
            get(){
                var token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                var validado = call.validateToken(token!!)
                if (!validado){
                    call.respond(HttpStatusCode.Unauthorized, "El token ha fallado")
                    return@get
                }
                var listRecetas = RecetasProviderUseCase.getAllRecetas()
                call.respond(listRecetas.invoke())
            }

            delete("{idReceta}") {
                var token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                var validado = call.validateToken(token!!)
                if (!validado){
                    call.respond(HttpStatusCode.Unauthorized, "El token ha fallado")
                    return@delete
                }
                var idReceta = call.parameters["idReceta"]
                ProviderUseCase.logger.warn("Se borrará la receta con id: $idReceta")
                idReceta?.let {
                    var respuesta = RecetasProviderUseCase.deleteRecetas(idReceta.toInt())
                    if(! respuesta){
                        call.respond(HttpStatusCode.NotFound, "Esa receta no existe")
                    }else {
                        call.respondText("Receta eliminada")
                    }
                }?: run{
                    call.respond(HttpStatusCode.NoContent, "NO hay receta preparada para eliminar")
                }
                return@delete
            }
            get("{idReceta}"){
                var token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                var validado = call.validateToken(token!!)
                if (!validado){
                    call.respond(HttpStatusCode.Unauthorized, "El token ha fallado")
                    return@get
                }
                var idReceta = call.parameters["idReceta"]
                if (idReceta==null){
                    call.respond(HttpStatusCode.BadRequest, "No has mandado id")
                    return@get
                }
                val receta = RecetasProviderUseCase.getRecetasById(idReceta.toInt())
                if (receta== null){
                    call.respond(HttpStatusCode.NotFound, "No hay receta con esa id")
                    return@get
                }
                call.respond(receta)
            }
            post(){
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                val validado = call.validateToken(token ?: "")

                if (!validado) {
                    call.respond(HttpStatusCode.Unauthorized, "El token ha fallado")
                    return@post
                }

                try {
                    val requestBody = call.receiveText()
                    println("JSON recibido: $requestBody")
                    val receta = Json.decodeFromString<Recetas>(requestBody)
                    val respuesta = RecetasProviderUseCase.postRecetas(receta)
                    if (respuesta == null) {
                        call.respond(HttpStatusCode.Conflict, "No se ha podido insertar")
                    } else {
                        call.respond(HttpStatusCode.Created, respuesta)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    call.respond(HttpStatusCode.BadRequest, "Error: ${e.localizedMessage}")
                }
            }

            patch("{idReceta}"){
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ") //token el header
                val validado = call.validateToken(token!!)
                if (!validado) {
                    call.respond(HttpStatusCode.Unauthorized, "El token ha fallado")
                    return@patch
                }
                try{
                    val idReceta = call.parameters["idReceta"]
                    idReceta?.let{
                        val updateReceta = call.receive<UpdateRecetas>()
                        val res = RecetasProviderUseCase.updateRecetas(idReceta.toInt(), updateReceta)
                        if (res==null){
                            call.respond(HttpStatusCode.Conflict, "Algo ha fallado al modificar")
                            return@patch
                        }
                        call.respond(HttpStatusCode.Created, "Se ha actualizado")
                    }?: run{
                        call.respond(HttpStatusCode.BadRequest,"Falta el id")
                        return@patch
                    }
                } catch (e: Exception){
                    call.respond(HttpStatusCode.BadRequest,"Algo ha fallado y ha saltado el catch")
                }
            }
        }
    }
}