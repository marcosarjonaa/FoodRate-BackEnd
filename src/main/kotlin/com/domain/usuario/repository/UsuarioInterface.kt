package com.domain.usuario.repository

import com.domain.usuario.models.UpdateUsuario
import com.domain.usuario.models.Usuario

interface UsuarioInterface {
    suspend fun getAllUsuario () : List<Usuario>
    suspend fun getUsuarioByEmail (email: String) : Usuario?
    suspend fun getUsuarioByDni (dni: String) : Usuario?
    suspend fun postUsuario (usuario: Usuario) : Boolean
    suspend fun updateUsuario (updateUsuario: UpdateUsuario, dni : String) : Boolean
    suspend fun deleteUsuario (dni: String) : Boolean
    suspend fun login (dni: String, pass: String) : Usuario?
    suspend fun register (usuario: UpdateUsuario) : Usuario?
}