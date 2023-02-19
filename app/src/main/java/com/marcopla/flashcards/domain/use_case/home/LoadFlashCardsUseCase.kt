package com.marcopla.flashcards.domain.use_case.home

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class LoadFlashCardsUseCase @Inject constructor(
    private val repository: FlashCardRepository
) {
    fun loadAll(): Flow<List<FlashCard>> {
        return repository.getFlashCards()
    }

    suspend fun loadById(flashCardId: Int): FlashCard {
        return repository.getFlashCardById(flashCardId)
    }
}