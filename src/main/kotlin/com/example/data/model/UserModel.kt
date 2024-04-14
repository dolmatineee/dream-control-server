package com.example.data.model

import io.ktor.server.auth.*

data class UserModel(
    val id: Int,
    val email: String,
    val login: String,
    val password: String,
    val isActive: Boolean = false,
    val role: RoleModel,
): Principal