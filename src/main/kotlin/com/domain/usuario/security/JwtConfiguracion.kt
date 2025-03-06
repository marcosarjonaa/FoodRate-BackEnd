package com.domain.usuario.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.jwt.*

object JwtConfiguracion {
    private const val secret = "mi_clave_supersecreta"
    private const val issuer = "domain.com"
    private const val audience = "ktor_audience"
    private const val realm = "ktor_realm"
    private val algorithm = Algorithm.HMAC256(secret)

    fun generarToken(dni: String): String {
        /**
         * No he puesto ninguna variable un expiratesAt
         * porque no quiero que se expire nunca, ya que
         * me viene mejor que no lo haga
         */
        return JWT.create()
            .withIssuer(issuer)
            .withAudience(audience)
            .withSubject("Authentication")
            .withClaim("dni", dni)
            .withClaim("time", System.currentTimeMillis())
            .sign(algorithm)
    }

    fun configureAuthentication(configuracion: JWTAuthenticationProvider.Config) {
        configuracion.realm = realm
        configuracion.verifier(
            JWT.require(algorithm)
                .withIssuer(issuer)
                .withAudience(audience)
                .build()
        )
        configuracion.validate { credencial ->
            if (credencial.payload.getClaim("dni").asString()!= null){
                JWTPrincipal(credencial.payload)
            }else null
        }
    }
}
