package com.marcopla.flashcards.domain.use_case.edit

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.data.repository.FlashCardRepository
import com.marcopla.flashcards.domain.use_case.add.InvalidBackTextException
import com.marcopla.flashcards.domain.use_case.add.InvalidFrontTextException

class EditUseCase(private val flashCardRepository: FlashCardRepository) {
    @Throws(DuplicateInsertionException::class)
    suspend fun invoke(frontText: String, backText: String) {
        if (frontText.isBlank()) {
            throw InvalidFrontTextException()
        }
        if (backText.isBlank()) {
            throw InvalidBackTextException()
        }
        flashCardRepository.edit(FlashCard(frontText = frontText, backText = backText))
    }
}