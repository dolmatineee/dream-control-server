package com.example.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String,
    val login: String,
    val password: String,
    val isActive: Boolean = false,
    val role: String,
)
