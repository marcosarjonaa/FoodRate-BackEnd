package com.ktor

import com.domain.recetas.models.UpdateRecetas
import com.domain.usuario.models.UpdateUsuario
import com.domain.usuario.usecase.ProviderUseCase
import com.domain.usuario.usecase.ProviderUseCase.logger
import com.ktor.routing.authRouting
import com.ktor.routing.recetasRouting
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        authRouting()
        recetasRouting()
        staticResources("/static", "static")
    }
}