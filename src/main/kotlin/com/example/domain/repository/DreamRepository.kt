package com.example.domain.repository

import com.example.data.model.DreamModel

interface DreamRepository {

    suspend fun addDream(dream: DreamModel)

    suspend fun getAllDreams(): List<DreamModel>

    suspend fun updateDream(dream: DreamModel, ownerId: Int)

    suspend fun deleteDream(dreamId: Int, ownerId: Int)
}