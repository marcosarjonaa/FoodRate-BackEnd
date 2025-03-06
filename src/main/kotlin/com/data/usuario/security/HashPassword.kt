package com.data.usuario.security

import com.domain.recetas.security.PasswordHashInterface
import java.security.MessageDigest

object HashPassword : PasswordHashInterface{
    override fun hash(pass: String): String {
        var passArray = pass.toByteArray()
        var messageDigest = MessageDigest.getInstance("SHA-256") //El código de lenguaje
        var hashByte : ByteArray = messageDigest.digest(passArray)
        var hashHex = hashByte.joinToString("") {
            "%02x".format(it)
        }
        return hashHex
    }

    override fun verify(pass: String, passHash: String): Boolean {
        return hash(pass) == passHash
    }
}