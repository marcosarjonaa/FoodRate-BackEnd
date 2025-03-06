package com.domain.recetas.security

interface PasswordHashInterface {
    fun hash(pass: String): String
    fun verify(pass: String, passHash: String): Boolean
}