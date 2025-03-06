package com.domain.usuario.usecase

import com.domain.usuario.mapping.toUpdateUsuario
import com.domain.usuario.mapping.toUsuario
import com.domain.usuario.models.Usuario
import com.domain.usuario.repository.UsuarioInterface
import com.domain.usuario.security.JwtConfiguracion

class LoginUseCase (val repository : UsuarioInterface){
    suspend operator fun invoke(dni: String ?, password:String ?): Usuario? {
        if (dni.isNullOrBlank() || password.isNullOrBlank())
           return null
        else {
            return try{
                val usu = repository.login(dni, password)
                usu!!.token = JwtConfiguracion.generarToken(usu.dni)
                val updateUsuario = usu.toUpdateUsuario()
                var respuesta = repository.updateUsuario(updateUsuario, dni)
                return if (respuesta){
                    updateUsuario.toUsuario()
                }else {
                    null
                }
            }catch (e: Exception){
                println("Error en login:  ${e.localizedMessage}")
                return null
            }

        }
    }

}