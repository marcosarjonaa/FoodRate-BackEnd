package com.domain.usuario.usecase

import com.domain.usuario.models.Usuario
import com.domain.usuario.repository.UsuarioInterface

class InsertUsuarioUseCase  (val repository : UsuarioInterface){

    var usuario : Usuario? = null

    suspend operator fun invoke() : Boolean {
        /**
         * En caso de que el usuario esté vacio se devuelve null
         */
        return if (usuario == null) {
            false
        }else {
            repository.postUsuario(usuario!!)
        }
    }
}