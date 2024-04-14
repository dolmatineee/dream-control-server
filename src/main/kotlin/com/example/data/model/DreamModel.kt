package com.example.data.model

data class DreamModel(
    val id: Int,
    val owner: Int,
    val dreamTitle: String,
    val dreamDescription: String,
    val dreamDate: String,
    val isVerified: Boolean = false
)
