package com.domain.usuario.mapping

import com.data.usuario.persistence.models.UsuarioDAO
import com.domain.usuario.models.UpdateUsuario
import com.domain.usuario.models.Usuario

fun Usuario.toUpdateUsuario(): UpdateUsuario{
    return UpdateUsuario(
        dni = dni,
        name = name,
        email = email,
        password = password,
        token = token
    )
}

fun UpdateUsuario.toUsuario() : Usuario {
    return Usuario(
        dni = dni!!,
        name = name!!,
        email = email!!,
        password = password!!,
        token = token!!
    )
}

fun UsuarioDAO.toUsuario() : Usuario {
    val usuario = Usuario(
        dni = dni,
        name = name,
        email = email,
        password = password,
        token = token ?: "",
    )
    return usuario
}