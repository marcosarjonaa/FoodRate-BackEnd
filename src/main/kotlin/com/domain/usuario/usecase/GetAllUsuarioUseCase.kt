package com.domain.usuario.usecase

import com.domain.usuario.models.Usuario
import com.domain.usuario.repository.UsuarioInterface

class GetAllUsuarioUseCase (val repository : UsuarioInterface){

    suspend operator fun invoke(): List<Usuario> = repository.getAllUsuario()
}