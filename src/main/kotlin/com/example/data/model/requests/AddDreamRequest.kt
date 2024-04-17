package com.example.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class AddDreamRequest(
    val id: Int? = null,
    val dreamTitle: String,
    val dreamDescription: String,
    val dreamDate: String,
    val isVerified: Boolean = false,
)
