package com.data.usuario.inmemory.repository

import com.data.recetas.persistence.models.RecetaTable
import com.data.usuario.persistence.models.UsuarioDAO
import com.data.usuario.persistence.models.UsuarioTable
import com.data.usuario.persistence.models.suspendTransaction
import com.domain.usuario.mapping.toUsuario
import com.domain.usuario.models.UpdateUsuario
import com.domain.usuario.models.Usuario
import com.domain.usuario.repository.UsuarioInterface
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.update

class MemoryUsuarioRepository : UsuarioInterface{
    override suspend fun getAllUsuario(): List<Usuario> {
        return suspendTransaction {
            UsuarioDAO.all().map { it.toUsuario() }
        }
    }

    override suspend fun getUsuarioByEmail(email: String): Usuario? {
        return suspendTransaction {
            UsuarioDAO.find {
                UsuarioTable.email eq email
            }.limit(1).map { it.toUsuario() }.firstOrNull()
        }
    }

    override suspend fun getUsuarioByDni(dni: String): Usuario? {
        return suspendTransaction {
            UsuarioDAO.find {
                UsuarioTable.dni eq dni
            }.limit(1).map { it.toUsuario() }.firstOrNull()
        }
    }

    override suspend fun postUsuario(usuario: Usuario): Boolean {
        val u = getUsuarioByDni(usuario.dni)
        return if (u == null){
            suspendTransaction {
                UsuarioDAO.new {
                    this.dni = usuario.dni
                    this.name = usuario.name
                    this.email = usuario.email
                    this.password = usuario.password
                    this.token = usuario.token
                }
            }
            true
        } else {
            false
        }
    }

    override suspend fun updateUsuario(updateUsuario: UpdateUsuario, dni: String): Boolean {
        var num = 0
        try {
            suspendTransaction {
                num = UsuarioTable.update ({
                    UsuarioTable.dni eq dni
                }) {usu ->
                    updateUsuario.name?.let { usu[name] = it }
                    updateUsuario.email?.let { usu[email] = it }
                    updateUsuario.password?.let { usu[password] = it }
                    updateUsuario.token?.let { usu[token] = it }
                }
            }

        }catch (e: Exception){
            e.printStackTrace()
            false
        }
        return num == 1
    }

    override suspend fun deleteUsuario(dni: String): Boolean = suspendTransaction {
        val num = UsuarioTable.deleteWhere { UsuarioTable.dni eq id }
        num==1
    }

    override suspend fun login(dni: String, pass: String): Usuario? {
        TODO("Not yet implemented")
    }

    override suspend fun register(usuario: UpdateUsuario): Usuario? {
        TODO("Not yet implemented")
    }

}