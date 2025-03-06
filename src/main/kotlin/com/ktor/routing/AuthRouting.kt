package com.ktor.routing

import com.domain.usuario.models.UpdateUsuario
import com.domain.usuario.models.Usuario
import com.domain.usuario.usecase.ProviderUseCase
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.ktor.validateToken

fun Route.authRouting(){
    route("/auth"){
        post(){
            try {
                var requestLogin = call.receive<UpdateUsuario>()
                var loguear: Usuario? = ProviderUseCase.login(requestLogin.dni, requestLogin.password)
                if (loguear!=null){
                    val token = loguear!!.token
                    call.respondText("Token: "+token)
                } else {
                    call.respond(loguear.toString())
                }
            }catch (e: Exception){
                println(e.localizedMessage)
            }
        }
    }

    route("/register"){
        post(){
            try {
                var usuario: UpdateUsuario = call.receive<UpdateUsuario>()
                var registrar = ProviderUseCase.register(usuario)
                if (registrar != null){
                    call.respond(HttpStatusCode.Created, "Todo correcto")
                }else {
                    call.respond(HttpStatusCode.Conflict, "Algo ha fallado")
                }
            }catch(e: Exception){
                call.respond(HttpStatusCode.BadRequest, "Ha habido una excepcion")
            }
        }
    }

    route("/usuario"){
        authenticate("jwt-auth") {
            get ("{dni}") {
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                var validado = call.validateToken(token!!)
                if(!validado){
                    call.respond(HttpStatusCode.Unauthorized, "EL token ha fallado")
                    return@get
                }
                var dni = call.parameters["dni"]
                if (dni == null){
                    call.respond(HttpStatusCode.BadRequest, "Necesitas el dni para buscar")
                    return@get
                }

                val usuario = ProviderUseCase.getUsuarioByDni(dni!!)
                if (usuario== null){
                    call.respond(HttpStatusCode.BadRequest, "Usuairo no existente")
                    return@get
                }
                call.respond(usuario)
            }
        }
    }
}