package com.domain.usuario.usecase

import com.domain.usuario.models.Usuario
import com.domain.usuario.repository.UsuarioInterface

class GetUsuarioByDniUseCase (val repository : UsuarioInterface) {
    var dni : String? = null


    suspend operator fun invoke() : Usuario? {
        return if (dni?.isNullOrBlank() == true)
                null
            else{
                repository.getUsuarioByDni(dni!!)
        }
    }
}