package com.data.usuario.persistence.repository

import com.data.usuario.persistence.models.UsuarioDAO
import com.data.usuario.persistence.models.UsuarioTable
import com.data.usuario.persistence.models.suspendTransaction
import com.data.usuario.security.HashPassword
import com.domain.usuario.mapping.toUsuario
import com.domain.usuario.models.UpdateUsuario
import com.domain.usuario.models.Usuario
import com.domain.usuario.repository.UsuarioInterface
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.update

class PersistenceUsuarioRepository : UsuarioInterface{
    override suspend fun getAllUsuario(): List<Usuario> {
        return suspendTransaction {
            UsuarioDAO.all().map { it.toUsuario() }
        }
    }

    override suspend fun getUsuarioByEmail(email: String): Usuario? {
        return suspendTransaction {
            UsuarioDAO.find {
                UsuarioTable.email eq email
            }.map { it.toUsuario() }.firstOrNull()
        }
    }

    override suspend fun getUsuarioByDni(dni: String): Usuario? {
        try {
            return suspendTransaction {
                UsuarioDAO.find {
                    UsuarioTable.dni eq dni
                }.limit(1).map { it.toUsuario() }.firstOrNull()
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }

    override suspend fun postUsuario(usuario: Usuario): Boolean {
        val u = getUsuarioByDni(usuario.dni);
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
        }else {
            false
        }
    }

    override suspend fun updateUsuario(updateUsuario: UpdateUsuario, dni: String): Boolean {
       var num = 0
        try {
            suspendTransaction {
                num = UsuarioTable
                    .update({
                        UsuarioTable.dni eq dni
                    }) {
                        u ->
                        updateUsuario.name?.let { u[name] = it }
                        updateUsuario.email?.let { u[email] = it }
                        updateUsuario.password?.let { u[password] = it }
                        updateUsuario.token?.let { u[token] = it }
                    }
            }
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
        return num==1
    }

    override suspend fun deleteUsuario(dni: String): Boolean = suspendTransaction {
        val num = UsuarioTable.deleteWhere { UsuarioTable.dni eq dni  }
        num == 1
    }

    override suspend fun login(dni: String, pass: String): Usuario? {
        val  usuario : Usuario = getUsuarioByDni(dni) ?: return null
        return try {
            val hash = HashPassword.hash(pass)
                usuario

        } catch (e: Exception){
            println("Error en la autentication")
            null
        }
    }

    override suspend fun register(usuario: UpdateUsuario): Usuario? {
        return try {
            suspendTransaction {
                UsuarioDAO.new {
                    this.dni = usuario.dni!!
                    this.name = usuario.name!!
                    this.email = usuario.email!!
                    this.token = usuario.token!!
                }
            }.let {
                it.toUsuario()
            }
        } catch (e: Exception){
            println("Error en el registro")
            null
        }
    }
}