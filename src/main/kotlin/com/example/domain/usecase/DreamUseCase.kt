package com.example.domain.usecase

import com.example.data.model.DreamModel
import com.example.domain.repository.DreamRepository

class DreamUseCase(
    private val dreamRepository: DreamRepository
) {

    suspend fun addDream(dream: DreamModel) {
        dreamRepository.addDream(dream = dream)
    }

    suspend fun getAllDreams(): List<DreamModel> {
        return dreamRepository.getAllDreams()
    }

    suspend fun updateDream(dream: DreamModel, ownerId: Int) {
        dreamRepository.updateDream(dream = dream, ownerId = ownerId)
    }

    suspend fun deleteDream(dreamId: Int, ownerId: Int) {
        dreamRepository.deleteDream(dreamId = dreamId, ownerId = ownerId)
    }
}