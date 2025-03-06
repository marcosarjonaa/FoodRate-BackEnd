package com.ktor

import com.domain.usuario.security.JwtConfiguracion
import com.domain.usuario.usecase.ProviderUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSecurity(){

    install(Authentication ){
        jwt("jwt-auth") {
            JwtConfiguracion.configureAuthentication(this)
        }
    }

    routing {
        authenticate("jwt-auth") {
            get("/protected") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal?.getClaim("username", String::class)
                call.respondText("Hello, $username! You are authenticated.")
            }
        }
    }
}

suspend fun ApplicationCall.validateToken(token: String): Boolean{
    val usuarioData = this.principal<JWTPrincipal>()
    val dni = usuarioData?.payload?.getClaim("dni")?.asString()
    val usuario = ProviderUseCase.getUsuarioByDni(dni!!)
    if (usuario == null || token != usuario.token){
        this.respond(HttpStatusCode.Unauthorized, "Ha fallado el token o usuario")
        return false
    }else
        return true


}

