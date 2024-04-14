package com.example.domain.usecase

import com.example.data.model.DreamModel
import com.example.domain.repository.DreamRepository

class DreamUseCase(
    private val cardRepository: DreamRepository
) {

    suspend fun addDream(dream: DreamModel) {
        cardRepository.addDream(dream = dream)
    }

    suspend fun getAllDreams(): List<DreamModel> {
        return cardRepository.getAllDreams()
    }

    suspend fun updateDream(dream: DreamModel, ownerId: Int) {
        cardRepository.updateDream(dream = dream, ownerId = ownerId)
    }

    suspend fun deleteDream(dreamId: Int, ownerId: Int) {
        cardRepository.deleteDream(dreamId = dreamId, ownerId = ownerId)
    }
}