package com.ktor

import io.ktor.server.application.*

/*
1.- EngineMain.main(args) le indica a ktor que cargue la configuración application.conf, pero antes arranca el servidor Netty.
2.- Se buscará automaticamente el ichero application.conf ó application.yaml por orden.
3.- Se lee la configuración y se establece qué modulos se deberán cargar.
4.- kotlin, utiliza la reflexion para buscar la función module() en tiempo de ejecución y llamarla sin tener que hacerse
de forma explícita.
 */

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.myModule() {
    configureContext(this)
    configureSerialization()
    configureSecurity()
    configureRouting()
    configureDatabases()
}
