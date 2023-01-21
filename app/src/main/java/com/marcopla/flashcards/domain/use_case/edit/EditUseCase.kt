package com.marcopla.flashcards.domain.use_case.edit

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.data.repository.FlashCardRepository

class EditUseCase(private val flashCardRepository: FlashCardRepository) {
    @Throws(DuplicateInsertionException::class)
    suspend fun invoke(frontText: String, backText: String) {
        flashCardRepository.add(FlashCard(frontText = frontText, backText = backText))
    }
}