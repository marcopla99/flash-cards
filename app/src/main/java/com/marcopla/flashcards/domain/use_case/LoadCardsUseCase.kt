package com.marcopla.flashcards.domain.use_case

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepository

class LoadCardsUseCase(private val repository: FlashCardRepository) {
    suspend fun invoke(): List<FlashCard> {
        return repository.getFlashCards()
    }
}