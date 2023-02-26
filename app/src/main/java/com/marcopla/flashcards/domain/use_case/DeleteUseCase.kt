package com.marcopla.flashcards.domain.use_case

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.FlashCardRepository

class DeleteUseCase(private val repository: FlashCardRepository) {
    fun invoke(flashCard: FlashCard) {
        repository.delete(flashCard)
    }
}