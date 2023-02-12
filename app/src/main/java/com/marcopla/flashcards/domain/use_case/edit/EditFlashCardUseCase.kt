package com.marcopla.flashcards.domain.use_case.edit

import com.marcopla.flashcards.data.model.FlashCard
import com.marcopla.flashcards.data.repository.DuplicateInsertionException
import com.marcopla.flashcards.data.repository.FlashCardRepository
import com.marcopla.flashcards.domain.use_case.exceptions.InvalidBackTextException
import com.marcopla.flashcards.domain.use_case.exceptions.InvalidFrontTextException

class EditFlashCardUseCase(private val flashCardRepository: FlashCardRepository) {
    @Throws(
        InvalidFrontTextException::class,
        InvalidBackTextException::class,
        DuplicateInsertionException::class,
    )
    suspend fun invoke(flashCard: FlashCard) {
        if (flashCard.frontText.isBlank()) {
            throw InvalidFrontTextException()
        }
        if (flashCard.backText.isBlank()) {
            throw InvalidBackTextException()
        }
        flashCardRepository.edit(flashCard)
    }
}