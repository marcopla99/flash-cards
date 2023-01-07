package com.marcopla.flashcards.domain.use_case

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepositoryImpl

class LoadCardsUseCase(private val repository: FlashCardRepositoryImpl) {
    suspend fun invoke(): List<FlashCard> {
        return repository.getFlashCards()
    }
}