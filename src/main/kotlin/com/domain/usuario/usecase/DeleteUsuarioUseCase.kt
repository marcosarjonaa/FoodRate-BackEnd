package com.domain.usuario.usecase

import com.domain.usuario.repository.UsuarioInterface

class DeleteUsuarioUseCase (val repository : UsuarioInterface){
    var dni : String? = null

    suspend operator fun invoke() : Boolean {
        return if (dni == null) {
            false
        }else{
            return repository.deleteUsuario(dni!!)
        }

    }
}